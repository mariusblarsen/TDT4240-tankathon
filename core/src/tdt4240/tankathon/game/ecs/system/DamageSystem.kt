package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.Logger
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*


private val LOG: Logger = logger<DamageSystem>()

class DamageSystem : IteratingSystem(
        allOf(AIComponent::class, PositionComponent::class, PhysicsComponent::class).get()) {
    private val playerBoundingBox = Rectangle()
    private val enemyBoundingBox = Rectangle()
    private val bulletBoundingBox = Rectangle()
    private val playerEntities by lazy {
        engine.getEntitiesFor(
            allOf(PlayerComponent::class).get())
    }
    private val bulletEntities by lazy {
        engine.getEntitiesFor(
            allOf(BulletComponent::class).get())
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val positionComponent = entity.get(PositionComponent.mapper)
        require(positionComponent != null){ "Entity |entity| must have a positionComponent. entity=$entity"}
        val physicsComponent = entity.get(PhysicsComponent.mapper)
        require(physicsComponent != null){ "Entity |entity| must have a PhysicsComponent. entity=$entity"}

        enemyBoundingBox.set(
                positionComponent.position.x,
                positionComponent.position.y,
                physicsComponent.width,
                physicsComponent.height,
        )
        playerEntities.forEach{ player ->
            player[PositionComponent.mapper]?.let { playerPosition ->
                playerBoundingBox.set(
                        playerPosition.position.x,
                        playerPosition.position.y,
                        player[PhysicsComponent.mapper]!!.width,
                        player[PhysicsComponent.mapper]!!.height
                )
                if(playerBoundingBox.overlaps(enemyBoundingBox)){
                    enemyHit(player, entity)
                }
            }
        }
        bulletEntities.forEach{ bullet ->
            bullet[PositionComponent.mapper]?.let { bulletPosition ->
                bulletBoundingBox.set(
                        bulletPosition.position.x,
                        bulletPosition.position.y,
                        bullet[PhysicsComponent.mapper]!!.width,
                        bullet[PhysicsComponent.mapper]!!.height
                )
                if(bulletBoundingBox.overlaps(enemyBoundingBox)){
                    bulletHit(bullet, entity)
                }
            }
        }
    }
    private fun bulletHit(bullet: Entity, enemy: Entity){
        val bulletDamage = bullet.getComponent(DamageComponent::class.java).damage
        enemy[HealthComponent.mapper]?.run {
            health -= bulletDamage
        }

        bullet.addComponent<RemoveComponent>(engine as ECSengine)
    }

    private fun enemyHit(player: Entity, enemy: Entity){
        val npcDamage = enemy.getComponent(DamageComponent::class.java).damage
        player[HealthComponent.mapper]?.run {
            health -= npcDamage
        }
        enemy.addComponent<RemoveComponent>(engine as ECSengine)
    }

}
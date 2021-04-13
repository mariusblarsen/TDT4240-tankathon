package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.Logger
import ktx.log.debug
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*

private const val DAMAGE_AREA_HEIGHT= 2f
private const val DAMAGE_PER_SECOND = 25f
private const val DEATH_EXPLOSION_DURATION = 0.9f

private val LOG: Logger = logger<DamageSystem>()

class DamageSystem( private val ecsEngine: ECSengine) : IteratingSystem(allOf( AIComponent::class, TransformComponent::class, PositionComponent:: class).get()) {
    private val playerBoundingBox = Rectangle()
    private val enemyBoundingBox = Rectangle()
    private val playerEntities by lazy { ecsEngine.getEntitiesFor(
            allOf(PlayerComponent::class).get()) }
    private val enemyEntities by lazy { ecsEngine.getEntitiesFor(
            allOf(AIComponent::class).get()
    )
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        
        val positionComponent = entity.get(PositionComponent.mapper)
        require(positionComponent != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val AIComponent = entity[AIComponent.mapper]
        require(AIComponent != null){ "Entity |entity| must have a AIComponent. entity=$entity"}

        playerEntities.forEach{ player ->
            player[PositionComponent.mapper]?.let { playerPosition ->
                playerBoundingBox.set(
                        playerPosition.position.x,
                        playerPosition.position.y,
                        1f,
                        1f
                )
                enemyBoundingBox.set(
                        positionComponent.position.x,
                        positionComponent.position.y,
                        1f,
                        1f,
                )
                if(playerBoundingBox.overlaps(enemyBoundingBox)){
                    enemyHit(player, entity)
                }
            }
        }
    }
    private fun enemyHit(player: Entity, enemy: Entity){
        enemy.addComponent<RemoveComponent>(ecsEngine)
        LOG.info { "EnemyHit" }
    }

}
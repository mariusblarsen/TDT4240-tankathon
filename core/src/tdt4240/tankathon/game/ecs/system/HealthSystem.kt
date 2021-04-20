package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.ashley.has
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.UNIT_SCALE
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*
import kotlin.math.max

class HealthSystem(private val game: TankathonGame) : IteratingSystem(allOf(HealthComponent::class).get()) {
    private val shapeRenderer =  ShapeRenderer()
    private var healthbarHeight = 8 * UNIT_SCALE

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val healthComponent = entity[HealthComponent.mapper]
        require(healthComponent != null){ "Entity |entity| must have a healthComponent. entity=$entity"}
        val enemyScoreComponent =entity[EnemyScoreComponent.mapper]
        require(enemyScoreComponent != null){ "Entity |entity| must have a healthComponent. entity=$entity"}

        val healthPercentage = healthComponent.health/healthComponent.maxHealth
        val healthbarWidth = max(0f, 3f*healthPercentage)
        entity[EnemyScoreComponent.mapper]?.run{
            scorePercentage= healthPercentage
        }


        if (healthComponent.health <= 0){
            entity[EnemyScoreComponent.mapper]?.run{
                isDead= true
            }
            if(enemyScoreComponent.isScored) {
                entity.addComponent<RemoveComponent>(engine as ECSengine)
            }
        }

        val position = entity[PositionComponent.mapper]
        val size = entity[PhysicsComponent.mapper]
        if (position != null && size != null && healthPercentage < 1){
            shapeRenderer.projectionMatrix = game.batch.projectionMatrix
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.color = Color.RED
            shapeRenderer.rect(
                    position.position.x - size.width/2,
                    position.position.y + size.height,
                    healthbarWidth,
                    healthbarHeight)
            shapeRenderer.end()
        }
    }
}
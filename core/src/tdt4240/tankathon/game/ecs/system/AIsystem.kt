package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.ecs.component.*

class AIsystem() : IteratingSystem(
        allOf(AIComponent::class, VelocityComponent::class, PositionComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val position = entity[PositionComponent.mapper]
        require(position != null) { "Entity |entity| must have a PositionComponent. entity=$entity" }

        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null) { "Entity |entity| must have a VelocityComponent. entity=$entity" }

        val AI = entity[AIComponent.mapper]
        require(AI != null) { "Entity |entity| must have a SpriteComponent. entity=$entity" }


        //Velocity set to chaze player
        if (AI.enemies.isNotEmpty()) {
            val enemyPosition = AI.enemies.first().get<PositionComponent>()
            if (enemyPosition != null) {
                //velocity.direction.set(0f,1f,0f)//.set(enemyPosition.position.cpy().add(position.position)).nor()
                velocity.direction.x=enemyPosition.position.x-position.position.x
                velocity.direction.y=enemyPosition.position.y-position.position.y
                velocity.direction.nor()

            }
        }




    }
}

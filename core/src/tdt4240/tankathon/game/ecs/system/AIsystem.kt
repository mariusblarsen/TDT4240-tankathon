package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.ecs.component.*

class AIsystem() : IteratingSystem(
        allOf(AIComponent::class, VelocityComponent::class, SpriteComponent::class,
                PositionComponent::class, TransformComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val position = entity[PositionComponent.mapper]
        require(position != null) { "Entity |entity| must have a PositionComponent. entity=$entity" }

        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null) { "Entity |entity| must have a VelocityComponent. entity=$entity" }

        val sprite = entity[SpriteComponent.mapper]
        require(sprite != null) { "Entity |entity| must have a SpriteComponent. entity=$entity" }

        val trans = entity[TransformComponent.mapper]
        require(sprite != null) { "Entity |entity| must have a TransformComponent. entity=$entity" }



        //velocity.direction.rotate(1f,1f,0f,0f)

        position.position.x += velocity.direction.x * velocity.speed
        //position.position.y += velocity.speed*velocity.direction.y


    }
}

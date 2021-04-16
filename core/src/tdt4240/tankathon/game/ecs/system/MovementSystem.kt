package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.contains
import ktx.ashley.get
import ktx.log.Logger
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*
import kotlin.math.sign


private val LOG: Logger = logger<MovementSystem>()

class MovementSystem(private val ecSengine: ECSengine) : IteratingSystem(
        allOf(PositionComponent::class, VelocityComponent::class,
        PhysicsComponent::class).get()
){

    private val mapObjects by lazy {
        ecSengine.getEntitiesFor(allOf(MapObjectComponent::class).get())
    }
    private val padding: Float = 0.1f
    var collisionX: Boolean = false
    var collisionY: Boolean = false

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}
        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}
        val physicsComponent = entity[PhysicsComponent.mapper]
        require(physicsComponent != null){ "Entity |entity| must have a PhysicsComponent. entity=$entity"}

        var collisionX = false
        var collisionY = false

        val stepX = velocity.direction.x*velocity.speed*deltaTime
        val stepY = velocity.direction.y*velocity.speed*deltaTime

        val movingHitbox = Rectangle(
                position.position.x,
                position.position.y,
                physicsComponent.width,
                physicsComponent.height
        )
        val hitX = Rectangle(movingHitbox).apply {
            x += stepX + sign(stepX)*padding
        }
        val hitY = Rectangle(movingHitbox).apply {
            y += stepY + sign(stepY)*padding
        }

        mapObjects.forEach{ objectEntity ->
            objectEntity[MapObjectComponent.mapper]?.let { mapComponent ->
                val obstacle = mapComponent.hitbox

                if (hitX.overlaps(obstacle)){
                    collisionX = true
                    if(entity.contains(BulletComponent.mapper)){
                        entity.addComponent<RemoveComponent>(ecSengine)
                    }
                }
                if (hitY.overlaps(obstacle)){
                    collisionY = true
                    if(entity.contains(BulletComponent.mapper)){
                        entity.addComponent<RemoveComponent>(ecSengine)
                    }
                }
            }
        }
        if (!collisionX){
            position.position.x += stepX
        }
        if (!collisionY){
            position.position.y += stepY
        }
    }
}
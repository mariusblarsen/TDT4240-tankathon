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


private val LOG: Logger = logger<MovementSystem>()

class MovementSystem(private val ecSengine: ECSengine) : IteratingSystem(
        allOf(PositionComponent::class, VelocityComponent::class).get()
){

    private val mapObjects by lazy {
        ecSengine.getEntitiesFor(allOf(MapObjectComponent::class).get())
    }
    private val threshold: Float = 0.1f
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}
        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        val physicsComponent = entity[PhysicsComponent.mapper]

        val movingHitbox = Rectangle(
                position.position.x,
                position.position.y,
                1f,  // TODO: Get physics for bullet
                1f)
        if (physicsComponent != null) {
            movingHitbox.width = physicsComponent.width
            movingHitbox.height = physicsComponent.height
        }

        var collisionOnRight: Boolean
        var collisionOnLeft : Boolean
        var movingRight: Boolean
        var collisionUpwards: Boolean
        var collisionDownwards: Boolean
        var movingUp: Boolean
        var horizontalCollision: Boolean
        mapObjects.forEach{ objectEntity ->
            objectEntity[MapObjectComponent.mapper]?.let { mapComponent ->
                val obstacle = mapComponent.hitbox

                if (movingHitbox.overlaps(obstacle)){
                    if(entity.contains(BulletComponent.mapper)){
                        entity.addComponent<RemoveComponent>(ecSengine)
                    }
                    movingRight = velocity.direction.x > 0
                    collisionOnRight = movingHitbox.x < obstacle.x
                    collisionOnLeft = movingHitbox.x > obstacle.x + obstacle.width - threshold

                    movingUp = velocity.direction.y > 0
                    collisionUpwards = movingHitbox.y < obstacle.y + threshold
                    collisionDownwards = movingHitbox.y + threshold > obstacle.y + obstacle.height

                    horizontalCollision = (movingHitbox.y > obstacle.y + threshold) && (movingHitbox.y < obstacle.y + obstacle.height - threshold)
                    if (horizontalCollision){
                        position.position.y+=velocity.direction.y*velocity.speed*deltaTime
                        if ((movingRight and collisionOnLeft)
                                or (!movingRight and collisionOnRight)){
                            position.position.x+=velocity.direction.x*velocity.speed*deltaTime
                        }
                    } else {
                        position.position.x+=velocity.direction.x*velocity.speed*deltaTime
                        if ((movingUp and collisionDownwards) or (!movingUp and collisionUpwards)) {
                            position.position.y += velocity.direction.y * velocity.speed * deltaTime
                        }
                    }
                    return
                }
            }
        }
        position.position.x+=velocity.direction.x*velocity.speed*deltaTime
        position.position.y+=velocity.direction.y*velocity.speed*deltaTime
    }
}
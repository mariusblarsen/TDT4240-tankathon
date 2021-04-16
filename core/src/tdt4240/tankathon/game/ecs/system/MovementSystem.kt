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
        allOf(PositionComponent::class, VelocityComponent::class,
        PhysicsComponent::class).get()
){

    private val mapObjects by lazy {
        ecSengine.getEntitiesFor(allOf(MapObjectComponent::class).get())
    }
    private val threshold: Float = 0.1f
    var collisionOnRight: Boolean = false
    var collisionOnLeft : Boolean = false
    var movingRight: Boolean = false
    var collisionUpwards: Boolean = false
    var collisionDownwards: Boolean = false
    var movingUp: Boolean = false
    var horizontalCollision: Boolean = false
    var verticalCollision: Boolean = false

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}
        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        val physicsComponent = entity[PhysicsComponent.mapper]
        require(physicsComponent != null){ "Entity |entity| must have a PhysicsComponent. entity=$entity"}
        val movingHitbox = Rectangle(
                position.position.x,
                position.position.y,
                physicsComponent.width,
                physicsComponent.height
        )
        movingRight = velocity.direction.x > 0
        movingUp = velocity.direction.y > 0


        mapObjects.forEach{ objectEntity ->
            objectEntity[MapObjectComponent.mapper]?.let { mapComponent ->
                val obstacle = mapComponent.hitbox

                if (movingHitbox.overlaps(obstacle)){
                    if(entity.contains(BulletComponent.mapper)){
                        entity.addComponent<RemoveComponent>(ecSengine)
                    }

                    collisionOnRight = (movingHitbox.x < obstacle.x) || collisionOnRight
                    collisionOnLeft = (movingHitbox.x > obstacle.x + obstacle.width - threshold) ||collisionOnLeft

                    collisionUpwards = (movingHitbox.y < obstacle.y + threshold) ||collisionUpwards
                    collisionDownwards = (movingHitbox.y + threshold > obstacle.y + obstacle.height) ||collisionDownwards

                    horizontalCollision = ((movingHitbox.y > obstacle.y + threshold)
                            && (movingHitbox.y < obstacle.y + obstacle.height - threshold))
                            || horizontalCollision
                    verticalCollision = ((movingHitbox.x > obstacle.x + threshold)
                            && (movingHitbox.x < obstacle.x + obstacle.width - threshold))
                            || verticalCollision
                }
            }
        }
        if (horizontalCollision){
            if (!verticalCollision){
                position.position.y+=velocity.direction.y*velocity.speed*deltaTime
            }
            if ((movingRight and collisionOnLeft)
                    or (!movingRight and collisionOnRight)){
                position.position.x+=velocity.direction.x*velocity.speed*deltaTime
            }
        } else if (verticalCollision){
            position.position.x+=velocity.direction.x*velocity.speed*deltaTime
            if ((movingUp and collisionDownwards) or (!movingUp and collisionUpwards)) {
                position.position.y += velocity.direction.y * velocity.speed * deltaTime
            }
        } else {
            position.position.x+=velocity.direction.x*velocity.speed*deltaTime
            position.position.y+=velocity.direction.y*velocity.speed*deltaTime
        }

        collisionOnRight = false
        collisionOnLeft = false
        collisionUpwards = false
        collisionDownwards = false
        horizontalCollision = false
        verticalCollision = false
    }
}
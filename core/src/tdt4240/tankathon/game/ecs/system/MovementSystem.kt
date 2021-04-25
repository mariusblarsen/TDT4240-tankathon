package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.contains
import ktx.ashley.get
import ktx.log.Logger
import ktx.log.logger
import tdt4240.tankathon.game.ecs.component.*
import kotlin.math.sign


private val LOG: Logger = logger<MovementSystem>()

class MovementSystem : IteratingSystem(
        allOf(PositionComponent::class, VelocityComponent::class,
        PhysicsComponent::class).get()
) {

    private val mapObjects by lazy {
        engine.getEntitiesFor(allOf(MapObjectComponent::class).get())
    }
    private val padding: Float = 0.1f


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val position = entity[PositionComponent.mapper]
        require(position != null) { "Entity |entity| must have a PositionComponent. entity=$entity" }
        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null) { "Entity |entity| must have a VelocityComponent. entity=$entity" }
        val physicsComponent = entity[PhysicsComponent.mapper]
        require(physicsComponent != null) { "Entity |entity| must have a PhysicsComponent. entity=$entity" }

        var collisionX = false
        var collisionY = false

        val stepX = velocity.direction.x * velocity.speed * deltaTime
        val stepY = velocity.direction.y * velocity.speed * deltaTime

        val movingHitbox = Rectangle(
                position.position.x,
                position.position.y,
                physicsComponent.width,
                physicsComponent.height
        )

        if (entity.contains(AIComponent.mapper)) {
            if (!canMove(entity, movingHitbox)) {
                return
            }
        }

        val hitX = Rectangle(movingHitbox).apply {
            x += stepX + sign(stepX) * padding
        }
        val hitY = Rectangle(movingHitbox).apply {
            y += stepY + sign(stepY) * padding
        }

        mapObjects.forEach { objectEntity ->
            val obstacle = Rectangle()
            objectEntity[PositionComponent.mapper]?.let { position ->
                objectEntity[PhysicsComponent.mapper]?.let { size ->
                    obstacle.run {
                            x = position.position.x
                            y = position.position.y
                            width = size.width
                            height = size.height
                    }
                }
            }

            collisionX = hitX.overlaps(obstacle) || collisionX
            collisionY = hitY.overlaps(obstacle) || collisionY

            if ((collisionX || collisionY) && entity.contains(BulletComponent.mapper)) {
                entity.addComponent<RemoveComponent>(engine)
            }
            if (collisionX && collisionY) {
                return@forEach
            }
        }
        if (!collisionX) {
            position.position.x += stepX
        }
        if (!collisionY) {
            position.position.y += stepY
        }
    }

    private fun canMove(entity: Entity, hitbox: Rectangle): Boolean {
        var canMove = true
        val position = entity[PositionComponent.mapper] ?: return true
        val posZ = position.position.z
        val enemies = engine.getEntitiesFor(allOf(AIComponent::class, PositionComponent::class).get())
        enemies.forEach {
            val otherSize = it[PhysicsComponent.mapper] ?: return@forEach
            it[PositionComponent.mapper]?.let { otherPos ->
                if (it == entity) {
                    return@let
                }
                val otherHitbox = Rectangle(
                        otherPos.position.x, otherPos.position.y,
                        otherSize.width,
                        otherSize.height)
                if (otherHitbox.overlaps(hitbox)) {
                    if (otherPos.position.z > posZ) {
                        canMove = false
                        return@forEach
                    }
                }
            }
        }
        return canMove
    }
}
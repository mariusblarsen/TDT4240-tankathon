package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*

class AIsystem : IteratingSystem(
        allOf(AIComponent::class, VelocityComponent::class, PositionComponent::class, TransformComponent::class).get()
) {

    private val playerEntities by lazy { (engine as ECSengine).getEntitiesFor(
            allOf(PlayerComponent::class).get()) }
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val position = entity[PositionComponent.mapper]
        require(position != null) { "Entity |entity| must have a PositionComponent. entity=$entity" }

        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null) { "Entity |entity| must have a VelocityComponent. entity=$entity" }

        val AI = entity[AIComponent.mapper]
        require(AI != null) { "Entity |entity| must have a SpriteComponent. entity=$entity" }

        val transformComponent = entity[TransformComponent.mapper]
        require(transformComponent != null) { "Entity |entity| must have a TransformComponent. entity=$entity" }

        if (playerEntities == null){
            return
        }

        //Velocity set to chaze playe
        var distance=999999999999999f
        var closestEnemy = playerEntities.first()
        playerEntities.forEach{ player ->
            player[PositionComponent.mapper]?.let {
                if (distance>it.position.dst(position.position)){
                    distance = it.position.dst(position.position)
                    closestEnemy= player
                }
            }
        }

         val enemyPosition = closestEnemy[PositionComponent.mapper]
         if (enemyPosition != null) {
         transformComponent.rotationDeg = Vector2(velocity.direction.y, -velocity.direction.x).angleDeg()

         velocity.direction.x=enemyPosition.position.x-position.position.x
         velocity.direction.y=enemyPosition.position.y-position.position.y
         velocity.direction.nor()
        }




    }
}

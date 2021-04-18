package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*

class AIsystem(private val engine: ECSengine) : IteratingSystem(
        allOf(AIComponent::class, VelocityComponent::class, PositionComponent::class, TransformComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {

        val position = entity[PositionComponent.mapper]
        require(position != null) { "Entity |entity| must have a PositionComponent. entity=$entity" }

        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null) { "Entity |entity| must have a VelocityComponent. entity=$entity" }

        val AI = entity[AIComponent.mapper]
        require(AI != null) { "Entity |entity| must have a SpriteComponent. entity=$entity" }

        val transformComponent = entity[TransformComponent.mapper]
        require(transformComponent != null) { "Entity |entity| must have a TransformComponent. entity=$entity" }

        //Velocity set to chaze playe
        var itr=engine.players.iterator()
        var distance=999999999999999f
        var closestEnemy = engine.players.first()
        while (itr.hasNext()){
            var enemy= itr.next()
            if( enemy[PlayerComponent.mapper]?.team == AI.team){continue}

            var enemyPos = enemy[PositionComponent.mapper]?.position
            require(enemyPos != null) { "Entity |entity| must have a PositionComponent. entity=$entity" }

            if (distance>enemyPos.dst(position.position)){
                distance = enemyPos.dst(position.position)
                closestEnemy= enemy
            }
        }
         val enemyPosition = closestEnemy[PositionComponent.mapper]
         if (enemyPosition != null) {
         transformComponent.rotationDeg = Vector2(velocity.direction.y, -velocity.direction.x).angleDeg()

         //velocity.direction.set(0f,1f,0f)//.set(enemyPosition.position.cpy().add(position.position)).nor()
         velocity.direction.x=enemyPosition.position.x-position.position.x
         velocity.direction.y=enemyPosition.position.y-position.position.y
         velocity.direction.nor()
        }




    }
}

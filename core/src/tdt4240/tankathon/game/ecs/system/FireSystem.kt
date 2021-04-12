package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*

class FireSystem() : IteratingSystem(
        allOf(BulletComponent::class, VelocityComponent::class, SpriteComponent::class,
                PositionComponent::class, TransformComponent::class).get()
){
    private val bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val bullet = entity[BulletComponent.mapper]
        require(bullet != null){ "Entity |entity| must have a BulletComponent. entity=$entity"}

        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}

        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        val sprite = entity[SpriteComponent.mapper]
        require(sprite != null){ "Entity |entity| must have a SpriteComponent. entity=$entity"}

        //position.position.x += velocity.speed
        //position.position.y += velocity.speed
    }

}


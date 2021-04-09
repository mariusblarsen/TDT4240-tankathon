package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.Logger
import ktx.log.error
import ktx.log.logger
import tdt4240.tankathon.game.ecs.component.SpriteComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent

private val LOG: Logger = logger<RenderSystem>()

class RenderSystem(
        private val batch: Batch,
        private val gameViewport: Viewport,
        private val mapViewport: Viewport,
        backgroundTexture: Texture
        ): SortedIteratingSystem(
        allOf(TransformComponent::class, SpriteComponent::class).get(),
        compareBy { entity -> entity[SpriteComponent.mapper]}
){
    private val background = Sprite(backgroundTexture.apply {
        // TODO (Marius): No wrap, collision on edges.
        setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    })

    override fun update(deltaTime: Float) {
        mapViewport.apply()
        batch.use(mapViewport.camera.combined) {
            background.run {
                // TODO (Marius): Scroll-speed from tank-speed
                // TODO (Marius): Only scroll on movement
                //scroll(0.1f*deltaTime, 0.1f*deltaTime)
                draw(batch)
            }
        }

        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined){
            super.update(deltaTime)
        }
    }
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity[TransformComponent.mapper]
        require(transformComponent != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val spriteComponent = entity[SpriteComponent.mapper]
        require(spriteComponent != null){ "Entity |entity| must have a SpriteComponent. entity=$entity"}

        if (spriteComponent.sprite.texture == null){
            LOG.error{ "Entity has no texture for rendering. entity=$entity" }
            return
        }

        /* Render method */
        spriteComponent.sprite.run{
            rotation = transformComponent.rotationDeg
            setBounds(
                    transformComponent.position.x,
                    transformComponent.position.y,
                    transformComponent.size.x,
                    transformComponent.size.y)
            draw(batch)
        }
    }
}
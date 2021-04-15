package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.Logger
import ktx.log.error
import ktx.log.logger
import tdt4240.tankathon.game.ecs.component.PositionComponent
import tdt4240.tankathon.game.ecs.component.SpriteComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent

private val LOG: Logger = logger<RenderSystem>()

class RenderSystem(
        private val batch: Batch,
        private val gameViewport: Viewport,
        private val mapRenderer: OrthogonalTiledMapRenderer,
        private val gameCamera: OrthographicCamera
        ): SortedIteratingSystem(
        allOf(SpriteComponent::class, PositionComponent::class).get(),
        compareBy { entity -> entity[SpriteComponent.mapper]}
){

    override fun update(deltaTime: Float) {
        forceSort()
        gameViewport.apply()
        mapRenderer.setView(gameCamera)
        mapRenderer.render()

        batch.use(gameCamera.combined){
            super.update(deltaTime)
        }
    }
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transformComponent = entity[TransformComponent.mapper]
        val spriteComponent = entity[SpriteComponent.mapper]
        require(spriteComponent != null){ "Entity |entity| must have a SpriteComponent. entity=$entity"}
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}

        if (spriteComponent.sprite.texture == null){
            LOG.error{ "Entity has no texture for rendering. entity=$entity" }
            return
        }

        /* Render method */
        spriteComponent.sprite.run{
            if (transformComponent != null){
                rotation = transformComponent.rotationDeg
            }
            setPosition(position.position.x, position.position.y)
            draw(batch)
        }
    }
}
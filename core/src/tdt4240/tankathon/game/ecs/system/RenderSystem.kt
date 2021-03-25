package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.Logger
import ktx.log.error
import ktx.log.logger
import sun.font.GraphicComponent
import tdt4240.tankathon.game.ecs.component.SpriteComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent
import com.badlogic.ashley.systems.SortedIteratingSystem as SortedIteratingSystem1

private val LOG: Logger = logger<RenderSystem>()

class RenderSystem( private val batch: Batch,
                    private val gameViewport: Viewport

) : SortedIteratingSystem(
        allOf(GraphicComponent::class, TransformComponent::class).get(),
        compareBy<TransformComponent>{ entity -> entity[TransformComponent.mapper] },

        ){
    override fun update(deltaTime: Float) {

        forceSort()
        gameViewport.apply()
        batch.use(camera.combined) {
            super.update(deltaTime)
        }
    }
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity |entity| must have a TransformComponent. entity=$entity" }
        val graphic = entity[GraphicComponent.mapper]
        require(graphic != null) { "Entity |entity| must have a GraphicComponent. entity=$entity" }
        val spriteComponent = entity[SpriteComponent.mapper]
        require(spriteComponent != null){ "Entity |entity| must have a SpriteComponent. entity=$entity"}

        if (spriteComponent.sprite.texture == null){
            LOG.error{ "Entity has no texture for rendering. entity=$entity" }
            return
        }
        if (graphic.sprite.texture == null) {
            LOG.error { "Entity has no texture for rendering" }
            return
        }

        graphic.sprite.run {
            rotation = transform.rotationDeg
            setBounds(posX, posY, sizeX, sizeY)
            draw(batch)
        }

        /* Render method */
        spriteComponent.sprite.run{
            draw(batch)
        }
    }
}


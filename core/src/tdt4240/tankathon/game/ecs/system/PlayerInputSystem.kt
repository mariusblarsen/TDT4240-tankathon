package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.ecs.component.FacingComponent
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent

class PlayerInputSystem(
        private val gameViewport: Viewport
) : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, FacingComponent::class).get()
){
    private val inputVec = Vector2()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val facing = entity[FacingComponent.mapper]
        require(facing != null){ "Entity |entity| must have a FacingComponent. entity=$entity"}

        /* Handle input
        * :inputVec: position of input touch
        * set facing direction by subtracting position of tank
        * from input vector
        * */
        inputVec.x = Gdx.input.x.toFloat()
        inputVec.y = Gdx.input.y.toFloat()

        gameViewport.unproject(inputVec)
        val diffX = inputVec.x - transform.position.x - transform.size.x*0.5f
        val diffY = inputVec.y - transform.position.y - transform.size.y*0.5f

        facing.direction = Vector2(diffX, diffY)
    }
}


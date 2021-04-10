package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent

class PlayerInputSystem(
        private val gameViewport: Viewport)
    : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class).get()
){
    private val inputVec = Vector2()
    private val screenWidth = Gdx.graphics.width
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val player = entity[PlayerComponent.mapper]
        require(player != null){ "Entity |entity| must have a PlayerComponent. entity=$entity"}

        /* Handle input */
        /* Aiming */
        if (Gdx.input.x > screenWidth/2){
            inputVec.x = Gdx.input.x.toFloat()
            inputVec.y = Gdx.input.y.toFloat()

            gameViewport.unproject(inputVec)
            setRotation(inputVec, transform)
        }

        /* Handle input */
        /* Control tank */
        if (Gdx.input.x < screenWidth / 2) {
            inputVec.x = Gdx.input.x.toFloat()
            inputVec.y = Gdx.input.y.toFloat()

            gameViewport.unproject(inputVec)
            setVelocityDirection(inputVec, transform, deltaTime)
        }
        /* Move camera */
        gameViewport.camera.position.set(transform.position)
    }


    private fun setRotation(input: Vector2, transform: TransformComponent){
        /* Receives an Vector 2 representing the position of a touch.
        * Calculates the vector from center of right side of screen,
        * and sets the rotationDeg = angle of vector*/
        val joyStick = Vector2(Gdx.graphics.width *3f/4f, Gdx.graphics.height /2f)
        gameViewport.unproject(joyStick)
        transform.rotationDeg = Vector2(input.x - joyStick.x, input.y - joyStick.y).angleDeg()-90

    }
    private fun setVelocityDirection(input: Vector2, transform: TransformComponent, deltaTime: Float){
        val joyStick = Vector2(Gdx.graphics.width *1f/4f, Gdx.graphics.height /2f)


        gameViewport.unproject(joyStick)
        val velocity = Vector3(input.x - joyStick.x, input.y - joyStick.y,0f).nor().scl(transform.speed)
        transform.position.add(velocity.scl(deltaTime))
        Gdx.app.log("#input", input.toString());
        Gdx.app.log("#tankPost", transform.position.toString());

    }
}


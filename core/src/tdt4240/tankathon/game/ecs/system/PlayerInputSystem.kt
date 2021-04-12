package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.PositionComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent
import tdt4240.tankathon.game.ecs.component.VelocityComponent

class PlayerInputSystem(
        private val gameViewport: Viewport,
        private val engine: ECSengine)
    : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, PositionComponent::class).get()
){
    private val inputVec = Vector2()
    private val screenWidth = Gdx.graphics.width
    private val bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val player = entity[PlayerComponent.mapper]
        require(player != null){ "Entity |entity| must have a PlayerComponent. entity=$entity"}
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}
        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        /* Handle input */
        /* Aiming */
        if (Gdx.input.x > screenWidth/2){
            inputVec.x = Gdx.input.x.toFloat()
            inputVec.y = Gdx.input.y.toFloat()

            gameViewport.unproject(inputVec)
            setRotation(inputVec, transform)
            engine.addBullet(bulletTexture, position.position, Vector3(cosDeg(transform.rotationDeg+90), sinDeg(transform.rotationDeg+90),0f))  // TODO (Marius): Move? Not sure where
        }

        /* Handle input */
        /* Control tank */
        if (Gdx.input.x < screenWidth / 2) {
            inputVec.x = Gdx.input.x.toFloat()
            inputVec.y = Gdx.input.y.toFloat()

            gameViewport.unproject(inputVec)
            setVelocityDirection(inputVec, velocity, position, deltaTime)
        }
        /* Move camera */
        gameViewport.camera.position.set(position.position)
    }


    private fun setRotation(input: Vector2, transform: TransformComponent){
        /* Receives an Vector 2 representing the position of a touch.
        * Calculates the vector from center of right side of screen,
        * and sets the rotationDeg = angle of vector*/
        val joyStick = Vector2(Gdx.graphics.width *3f/4f, Gdx.graphics.height /2f)
        gameViewport.unproject(joyStick)
        transform.rotationDeg = Vector2(input.x - joyStick.x, input.y - joyStick.y).angleDeg()-90


    }
    private fun setVelocityDirection(input: Vector2, velocity: VelocityComponent,
                                     position: PositionComponent, deltaTime: Float){ // TODO (Marius): Add velocity component
        val joyStick = Vector2(Gdx.graphics.width *1f/4f, Gdx.graphics.height /2f)


        gameViewport.unproject(joyStick)
        velocity.direction.set(input.x - joyStick.x, input.y - joyStick.y,0f).nor()
    }
}


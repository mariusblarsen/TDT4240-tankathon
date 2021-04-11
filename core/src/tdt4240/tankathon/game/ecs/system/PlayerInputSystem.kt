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
import tdt4240.tankathon.game.UNIT_SCALE
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.PositionComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent
import tdt4240.tankathon.game.ecs.component.VelocityComponent

class PlayerInputSystem(
        private val gameViewport: Viewport,
        private val engine: ECSengine)
    : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, PositionComponent::class, VelocityComponent::class).get()
){
    private val inputVec = Vector2()
    private val screenWidth = Gdx.graphics.width
    private val bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))
    private val threshold: Float = 0.1f  // For map-boundaries
    private val backgroundTexture = Texture(Gdx.files.internal("map.png"))  // TODO: Fetch somehow
    private val playerTexture = Texture(Gdx.files.internal("tank.png"))  // TODO: Fetch somehow

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val player = entity[PlayerComponent.mapper]
        require(player != null){ "Entity |entity| must have a PlayerComponent. entity=$entity"}
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}
        val velocityComponent = entity[VelocityComponent.mapper]
        require(velocityComponent != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        /* Handle input */
        /* Aiming */
        if (Gdx.input.x > screenWidth/2){
            inputVec.x = Gdx.input.x.toFloat()
            inputVec.y = Gdx.input.y.toFloat()

            gameViewport.unproject(inputVec)
            val bulletPosition = Vector3(Gdx.graphics.width/2f, Gdx.graphics.height/2f, 0f)
            gameViewport.unproject(bulletPosition)
            val direction = setRotation(inputVec, transform)
            engine.addBullet(bulletTexture, bulletPosition, direction)  // TODO (Marius): Move? Not sure where
        }

        /* Handle input */
        /* Control tank */
        if (Gdx.input.x < screenWidth / 2) {
            inputVec.x = Gdx.input.x.toFloat()
            inputVec.y = Gdx.input.y.toFloat()

            gameViewport.unproject(inputVec)
            setVelocityDirection(inputVec, velocityComponent, position, deltaTime)
        }
        /* Move camera */
        gameViewport.camera.position.set(position.position)
    }


    private fun setRotation(input: Vector2, transform: TransformComponent): Vector2{
        /* Receives an Vector 2 representing the position of a touch.
        * Calculates the vector from center of right side of screen,
        * and sets the rotationDeg = angle of vector*/
        val joyStick = Vector2(Gdx.graphics.width *3f/4f, Gdx.graphics.height /2f)
        gameViewport.unproject(joyStick)

        val direction = Vector2(input.x - joyStick.x, input.y - joyStick.y)
        val rotation = direction.angleDeg()-90
        transform.rotationDeg = rotation
        return direction
    }
    private fun setVelocityDirection(input: Vector2, velocity: VelocityComponent,
                                     position: PositionComponent, deltaTime: Float){
        val joyStick = Vector2(Gdx.graphics.width *1f/4f, Gdx.graphics.height /2f)
        gameViewport.unproject(joyStick)
        velocity.direction = Vector3(input.x - joyStick.x, input.y - joyStick.y,0f)


        /* Check for map-boundaries */
        /* TODO: How to fetch these values from entities? */
        val height = backgroundTexture.height * UNIT_SCALE
        val width = backgroundTexture.width * UNIT_SCALE
        val playerHeight = playerTexture.height * UNIT_SCALE
        val playerWidth = playerTexture.width * UNIT_SCALE

        val nextPos = position.position.cpy().add(velocity.getVelocity().scl(deltaTime))
        /* x-direction */
        if (nextPos.x > threshold &&
                nextPos.x < width - playerWidth - threshold){
            position.position.x = nextPos.x
        }
        /* y-direction */
        if (nextPos.y > threshold &&
                nextPos.y < height - playerHeight - threshold){
            position.position.y = nextPos.y
        }
    }
}


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
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.PositionComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent
import tdt4240.tankathon.game.ecs.component.VelocityComponent


class PlayerInputSystem(
        private val gameViewport: Viewport)
    : IteratingSystem(
        allOf(PlayerComponent::class, TransformComponent::class, PositionComponent::class,
                CanonComponent::class, VelocityComponent::class).get()
){
    private val inputVecAim = Vector2()
    private val inputVecMove = Vector2()
    private val screenWidth = Gdx.app.graphics.width
    private val bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null){ "Entity |entity| must have a TransformComponent. entity=$entity"}
        val player = entity[PlayerComponent.mapper]
        require(player != null){ "Entity |entity| must have a PlayerComponent. entity=$entity"}
        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}
        val canon = entity[CanonComponent.mapper]
        require(canon != null){ "Entity |entity| must have a CanonComponent. entity=$entity"}
        val velocityComponent = entity[VelocityComponent.mapper]
        require(velocityComponent != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}
        val spriteComponent = entity[SpriteComponent.mapper]
        require(spriteComponent != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        /* Handle input */
        val pointer0x = Gdx.input.getX(0)  // pointer 0 first input
        val pointer0y = Gdx.input.getY(0)
        val pointer1x = Gdx.input.getX(1)  // point 1 second input
        val pointer1y = Gdx.input.getY(1)
        val pointer0touched = Gdx.input.isTouched(0)
        val pointer1touched = Gdx.input.isTouched(1)
        var pointer0onLeft = false
        var pointer1onLeft = false

        if (pointer0touched){
            if(pointer0x > screenWidth/2){
                inputVecAim.set(pointer0x.toFloat(),pointer0y.toFloat())
            } else {
                pointer0onLeft = true
                inputVecMove.set(pointer0x.toFloat(),pointer0y.toFloat())
            }
        }
        if (pointer1touched){
            if(pointer1x > screenWidth/2){
                inputVecAim.set(pointer1x.toFloat(),pointer1y.toFloat())
            } else {
                pointer1onLeft = true
                inputVecMove.set(pointer1x.toFloat(),pointer1y.toFloat())
            }
        }

        /* Aiming */
        if ((pointer0touched && !pointer0onLeft) || (pointer1touched && !pointer1onLeft)){
            gameViewport.unproject(inputVecAim)
            val direction = setRotation(inputVecAim, transform).nor()
            canon.timer -= deltaTime
            val playerPos = Vector2(position.position.x, position.position.y)
            gameViewport.project(playerPos)
            val bulletPosition = Vector3(
                    playerPos.x + spriteComponent.sprite.vertices[0],
                    playerPos.y - spriteComponent.sprite.vertices[1],
                    0f
            )
            gameViewport.unproject(bulletPosition)
            if (canon.timer < 0){
                canon.timer = canon.fireRate
                (engine as ECSengine).addBullet(bulletTexture, bulletPosition, direction)
            }
        }
        /* Control tank */
        if ((pointer0touched && pointer0onLeft) || (pointer1touched && pointer1onLeft)){
            gameViewport.unproject(inputVecMove)
            setVelocityDirection(inputVecMove, velocityComponent)
        } else {
            velocityComponent.direction.set(0f, 0f, 0f)
        }
        /* Move camera to player position */
        gameViewport.camera.position.set(position.position)
    }


    private fun setRotation(input: Vector2,
                            transform: TransformComponent): Vector2{
        /* Receives an Vector 2 representing the position of a touch.
        * Calculates the vector from center of right side of screen,
        * and sets the rotationDeg = angle of vector*/
        val joyStick = Vector2(Gdx.graphics.width *3f/4f, Gdx.graphics.height /2f)//prøvd å flytte joystick lenger ned og ut.tidligere verdier: (Gdx.graphics.width *3f/4f, Gdx.graphics.height /2f)
        gameViewport.unproject(joyStick)
        val direction = Vector2(input.x - joyStick.x, input.y - joyStick.y)
        val rotation = direction.angleDeg()-90
        transform.rotationDeg = rotation
        return direction
    }
    private fun setVelocityDirection(input: Vector2,
                                     velocity: VelocityComponent){
        val joyStick = Vector2(Gdx.graphics.width/4f, Gdx.graphics.height /2f)
        gameViewport.unproject(joyStick)
        velocity.direction = Vector3(input.x - joyStick.x, input.y - joyStick.y,0f).nor()
    }
}


package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.get
import ktx.ashley.mapperFor
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import kotlin.math.cos
import kotlin.math.sin

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent>{
    val position = Vector3()
    val size = Vector2(1f, 1f)
    var rotationDeg= 0f
    var velocityRotation = 0f

    override fun reset() {
        position.set(Vector3.Zero)
        size.set(1f,1f)
        rotationDeg=0f

    }
    override fun compareTo(other: TransformComponent): Int {
        val zDiff= position.z - other.position.z
        return (if(zDiff==0f) position.y-other.position.y else zDiff).toInt()
    }

    fun setRotation(input: Vector2){
        /* Receives an Vector 2 representing the position of a touch.
        * Calculates the vector from center of right side of screen,
        * and sets the rotationDeg = angle of vector*/
        val joyStick = Vector2(V_WIDTH*3f/4f, V_HEIGHT/2f)
        rotationDeg = Vector2(input.x - joyStick.x, input.y - joyStick.y).angleDeg()-90


    }
    fun setVelocityDirection(input: Vector2, deltaTime: Float){
        val joyStick = Vector2(V_WIDTH*1f/4f, V_HEIGHT/2f)

        // TODO(Get speed from enity instead of hardcode)
        val speed=1f

        var velocity = Vector3(input.x - joyStick.x, input.y - joyStick.y,0f).nor().scl(speed)
        position.add(velocity.scl(deltaTime))

    }

    companion object{
        val mapper:ComponentMapper<TransformComponent> = mapperFor<TransformComponent>()
    }
}
package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.get
import ktx.ashley.mapperFor
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
        velocityRotation = 0f

    }
    override fun compareTo(other: TransformComponent): Int {
        val zDiff= position.z - other.position.z
        return (if(zDiff==0f) position.y-other.position.y else zDiff).toInt()
    }

    fun setRotation(input: Vector2){
        /* Receives an Vector 2 representing the position of a touch.
        * Calculates the vector from center of right side of screen,
        * and sets the rotationDeg = angle of vector*/
        val joyStick = Vector2(3*Gdx.graphics.width/4f, Gdx.graphics.height/2f)
        val diffX = input.x //- joyStick.x  // TODO Øystein this
        val diffY = input.y //- joyStick.y
        rotationDeg = Vector2(diffX, diffY).angleDeg() - 90f
    }

    fun setVelocity(input: Vector2, deltaTime: Float){
        val diffX = input.x - position.x - size.x*0.5f
        val diffY = input.y - position.y - size.y*0.5f
        //velocityRotation = Vector2(diffX, diffY).angleDeg() - 90f

        position.x += diffX*deltaTime  // TODO: Normailze (.nor) const speed
        position.y += diffY*deltaTime
    /*
        position.x += cos(velocityRotation)*deltaTime
        position.y += sin(velocityRotation)*deltaTime
        */
    }

    companion object{
        val mapper:ComponentMapper<TransformComponent> = mapperFor<TransformComponent>()
    }
}
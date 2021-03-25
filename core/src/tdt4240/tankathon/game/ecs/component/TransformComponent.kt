package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.get
import ktx.ashley.mapperFor
import tdt4240.tankathon.game.V_WIDTH

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent>{
    val position = Vector3()
    val size = Vector2(1f, 1f)
    var rotationDeg= 0f

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
        * Calculates the vector from center to touch,
        * and sets the rotationDeg = angle of vector*/
        val diffX = input.x - position.x - size.x*0.5f
        val diffY = input.y - position.y - size.y*0.5f
        rotationDeg = Vector2(diffX, diffY).angleDeg() - 90f
    }

    companion object{
        val mapper:ComponentMapper<TransformComponent> = mapperFor<TransformComponent>()
    }
}
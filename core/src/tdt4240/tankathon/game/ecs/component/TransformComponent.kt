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
    var position = Vector3(0f,0f, 0f)
    val size = Vector2(1f, 1f)
    var rotationDeg= 0f
    var direction = Vector3()
    var speed = 1f

    override fun reset() {
        position.set(Vector3.Zero)
        size.set(1f,1f)
        rotationDeg=0f
        direction.set(Vector3.Zero)
        speed = 0f
    }
    override fun compareTo(other: TransformComponent): Int {
        val zDiff= position.z - other.position.z
        return (if(zDiff==0f) position.y-other.position.y else zDiff).toInt()
    }



    companion object{
        val mapper:ComponentMapper<TransformComponent> = mapperFor<TransformComponent>()
    }
}
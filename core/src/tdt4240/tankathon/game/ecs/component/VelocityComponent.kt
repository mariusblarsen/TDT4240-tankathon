package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class VelocityComponent : Component, Pool.Poolable{
    var direction = Vector3(1f,1f, 0f)
    var speed = 1f

    override fun reset() {
        direction.set(1f,1f, 0f)
        speed = 1f
    }

    fun getVelocity():Vector3{
        return direction.nor().scl(speed)
    }

    companion object{
        val mapper:ComponentMapper<VelocityComponent> = mapperFor<VelocityComponent>()
    }
}
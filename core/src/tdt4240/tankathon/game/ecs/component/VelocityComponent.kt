package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class VelocityComponent : Component, Pool.Poolable{
    var direction = Vector3()
    var speed = 0f

    override fun reset() {
        direction.set(Vector3.Zero)
        speed = 0f
    }

    companion object{
        val mapper:ComponentMapper<VelocityComponent> = mapperFor<VelocityComponent>()
    }
}
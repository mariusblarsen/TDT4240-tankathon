package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PositionComponent : Component, Pool.Poolable{
    val position = Vector3(0f,0f, 0f)

    override fun reset() {
        position.set(Vector3.Zero)
    }

    companion object{
        val mapper:ComponentMapper<PositionComponent> = mapperFor<PositionComponent>()
    }
}
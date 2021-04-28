package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PhysicsComponent: Component, Pool.Poolable {
    var width = 0f
    var height = 0f

    override fun reset() {
        height = 0f
        width = 0f
    }
    companion object {
        val mapper = mapperFor<PhysicsComponent>()
    }

}
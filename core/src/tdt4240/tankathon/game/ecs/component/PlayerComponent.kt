package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PlayerComponent: Component, Pool.Poolable {
    override fun reset() {
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
    var team: Int = 1

}
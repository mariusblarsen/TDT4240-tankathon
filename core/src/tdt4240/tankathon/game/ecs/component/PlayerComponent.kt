package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PlayerComponent: Component, Pool.Poolable {
    var health: Int = 3  // TODO: Use correct method to set initial health

    override fun reset() {
        health = 3
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }

}
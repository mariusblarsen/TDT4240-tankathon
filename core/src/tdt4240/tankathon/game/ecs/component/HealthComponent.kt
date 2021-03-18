package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

class HealthComponent: Component, Pool.Poolable {
    var maxHealth: Float = 1000.0f  // TODO: Find and set real value
    var health: Float = maxHealth

    override fun reset() {
        TODO("Not yet implemented")
    }
}
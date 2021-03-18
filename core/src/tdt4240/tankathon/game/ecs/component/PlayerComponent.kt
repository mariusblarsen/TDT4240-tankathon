package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool

class PlayerComponent: Component, Pool.Poolable {
    var health: Int = 3  // TODO: Use correct method to set initial health

    override fun reset() {
        health = 3
    }

}
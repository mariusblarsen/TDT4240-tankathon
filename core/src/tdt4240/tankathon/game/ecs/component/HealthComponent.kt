package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class HealthComponent: Component, Pool.Poolable {
    var maxHealth: Float = 1000.0f  // TODO: Find and set real value
    var health: Float = maxHealth

    override fun reset() {
        health = maxHealth
    }

    companion object {
        /* Perfomance
        * ktx's mapperFor uses hashes for direct lookup of
        * entities, with complexity O(1) instead of O(n)
        *  */
        val mapper: ComponentMapper<HealthComponent> = mapperFor<HealthComponent>()
    }
}
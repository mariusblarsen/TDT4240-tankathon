package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PhysicsComponent: Component, Pool.Poolable {
    var width = 0f
    var height = 0f

    /** Delegates.notNull
     * Returns a delegate for a read/write porperty,
     * that is initialized not during construction,
     * but at a later time.
     */

    override fun reset() {
        height = 0f
        width = 0f
    }
    companion object {
        val mapper = mapperFor<PhysicsComponent>()
    }

}
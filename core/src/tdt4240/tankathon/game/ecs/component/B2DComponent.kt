package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Pool
import kotlin.properties.Delegates

abstract class B2DComponent: Component, Pool.Poolable {
    private var body: Body? = null
    var width by Delegates.notNull<Float>()
    var height by Delegates.notNull<Float>()
    /** Delegates.notNull
     * Returns a delegate for a read/write porperty,
     * that is initialized not during construction,
     * but at a later time.
     */

    override fun reset() {
        body?.world?.destroyBody(body)
        body = null

        height = 0.0f
        width = 0.0f
    }

}
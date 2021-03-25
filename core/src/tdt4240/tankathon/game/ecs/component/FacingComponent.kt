package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class FacingComponent : Component, Pool.Poolable {
    var direction: Vector2 = Vector2(0f, 0f)

    override fun reset() {
        direction = Vector2(0f, 0f)
    }

    companion object {
        val mapper = mapperFor<FacingComponent>()

    }
}
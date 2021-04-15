package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class DamageComponent: Component, Pool.Poolable {
    var damage: Float = 0f

    override fun reset() {
        damage = 0f
    }

    companion object {
        val mapper: ComponentMapper<DamageComponent> = mapperFor<DamageComponent>()
    }
}
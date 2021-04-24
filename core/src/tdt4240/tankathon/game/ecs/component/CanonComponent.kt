package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class CanonComponent:  Component, Pool.Poolable {
    var fireRate = 0.1f //number of seconds before canon can refire
    var timer = 0f //countdown from fireRate downwards, can fire when timer < 0
    var damage = 0f


    override fun reset() {
        timer = 0f
        damage = 0f

    }

    companion object {
        val mapper: ComponentMapper<CanonComponent> = mapperFor<CanonComponent>()
    }
}
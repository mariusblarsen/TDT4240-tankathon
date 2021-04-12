package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class CanonComponent:  Component, Pool.Poolable {
    var fireRate = 4f //number of seconds before canon can refire
    var timer: Double = 0.0 //countdown from fireRate downwards, can fire when timer < 0
    var amunition = 9999999999


    override fun reset() {
        timer=0.0
        amunition = 9999999999

    }

    companion object {
        val mapper: ComponentMapper<CanonComponent> = mapperFor<CanonComponent>()
    }
}
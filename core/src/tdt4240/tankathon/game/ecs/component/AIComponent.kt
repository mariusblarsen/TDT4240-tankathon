package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class AIComponent: Component, Pool.Poolable {
    var team=0

    override fun reset() {
        team = 0
    }
    companion object{
        val mapper: ComponentMapper<AIComponent> = mapperFor()
    }
}
package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class AIComponent: Component, Pool.Poolable {
    var enemies = listOf<Entity>()
    override fun reset() {
        enemies = listOf<Entity>()
    }
    companion object{
        val mapper: ComponentMapper<AIComponent> = mapperFor()
    }
}
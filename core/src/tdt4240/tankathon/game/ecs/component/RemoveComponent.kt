package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor


class RemoveComponent : Component, Pool.Poolable {
    override fun reset(){

    }
    companion object{
        val mapper: ComponentMapper<RemoveComponent> = mapperFor<RemoveComponent>()
    }
}
package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class MapObjectComponent : Component, Pool.Poolable{
    override fun reset() {}


    companion object{
        val mapper: ComponentMapper<MapObjectComponent> = mapperFor<MapObjectComponent>()
    }
}
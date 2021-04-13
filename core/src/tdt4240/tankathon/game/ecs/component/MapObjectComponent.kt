package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class MapObjectComponent : Component, Pool.Poolable{
    var startPosition: Vector2 = Vector2(0f, 0f)
    var vertices = FloatArray(0)
    override fun reset() {
        startPosition = Vector2(0f, 0f)
        vertices = FloatArray(0)
    }

    companion object{
        val mapper: ComponentMapper<MapObjectComponent> = mapperFor<MapObjectComponent>()
    }
}
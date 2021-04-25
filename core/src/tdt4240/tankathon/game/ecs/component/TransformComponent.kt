package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent : Component, Pool.Poolable, Comparable<TransformComponent>{
    var position = Vector3(0f,0f, 0f) //TODO(Øystein, Marius) cant remove position because of z-diff
    val size = Vector2(1f, 1f)
    var rotationDeg= 0f

    override fun reset() {
        position.set(Vector3.Zero)
        size.set(1f,1f)
        rotationDeg=0f

    }
    override fun compareTo(other: TransformComponent): Int {
        val zDiff= position.z - other.position.z
        return (if(zDiff==0f) position.y-other.position.y else zDiff).toInt()
    }



    companion object{
        val mapper:ComponentMapper<TransformComponent> = mapperFor<TransformComponent>()
    }
}
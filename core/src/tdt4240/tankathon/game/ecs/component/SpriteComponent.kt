package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import tdt4240.tankathon.game.UNIT_SCALE

class SpriteComponent: Component, Pool.Poolable, Comparable<SpriteComponent> {
    var sprite: Sprite = Sprite()

    override fun reset() {
        sprite.texture = null
    }

    override fun compareTo(other: SpriteComponent): Int {
        return (sprite.color.a - other.sprite.color.a).toInt()
    }

    fun setTexture(texture: Texture, origin: Vector2){
        sprite.run{
            setRegion(texture)
            setSize(texture.width * UNIT_SCALE, texture.height* UNIT_SCALE)
            setOrigin(origin.x, origin.y)
        }
    }

    companion object {
        val mapper = mapperFor<SpriteComponent>()
    }
}
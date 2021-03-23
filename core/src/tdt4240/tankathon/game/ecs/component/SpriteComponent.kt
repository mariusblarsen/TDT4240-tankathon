package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Pool
import ktx.ashley.get
import ktx.ashley.mapperFor

class SpriteComponent: Component, Pool.Poolable, Comparable<SpriteComponent> {
    var sprite: Sprite = Sprite()

    override fun reset() {
        sprite.texture = null
        sprite.setColor(1f, 1f, 1f, 1f)
    }

    override fun compareTo(other: SpriteComponent): Int {
        /** TODO:
         *  Compare whos rendered on top/in back.
         *  Currently just a dummy-method to show how.
         *  This returns the difference in opacity between this and other
         */
        return (sprite.color.a - other.sprite.color.a).toInt()
    }
    companion object {
        val mapper = mapperFor<SpriteComponent>()
    }
}
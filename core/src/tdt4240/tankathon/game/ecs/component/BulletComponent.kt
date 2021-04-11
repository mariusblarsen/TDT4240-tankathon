package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class BulletComponent: Component, Pool.Poolable {
    var bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))

    override fun reset() {
        bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))
    }

    companion object {
        val mapper: ComponentMapper<BulletComponent> = mapperFor<BulletComponent>()
    }
}
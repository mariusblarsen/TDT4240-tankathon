package tdt4240.tankathon.game.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import tdt4240.tankathon.game.ecs.component.B2DComponent
import tdt4240.tankathon.game.ecs.component.PlayerComponent

/* PooledEngine
*
* Source: https://github.com/libgdx/ashley/wiki/Efficient-Entity-Systems-with-pooling
* */
class ECSengine: PooledEngine() {
    // TODO: Initialize Box2D world

    fun createPlayer(spawnPosition: Vector2) {
        val entity: Entity = this.createEntity()

        val playerComponent : PlayerComponent? = createComponent(PlayerComponent::class.java)
        entity.add(playerComponent)

        /* Add box2D physics to the player */
        val b2DComponent : B2DComponent? = createComponent(B2DComponent::class.java)
        entity.add(playerComponent)

    }
}
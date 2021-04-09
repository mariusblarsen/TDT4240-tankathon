package tdt4240.tankathon.game.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import tdt4240.tankathon.game.ecs.component.HealthComponent
import tdt4240.tankathon.game.ecs.component.PhysicsComponent
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.SpriteComponent

/* PooledEngine
*
* Source: https://github.com/libgdx/ashley/wiki/Efficient-Entity-Systems-with-pooling
* */
class ECSengine: PooledEngine() {
    // TODO: Initialize Box2D world

    fun createPlayer(spawnPosition: Vector2) {
        val entity: Entity = this.createEntity()
        val position: Vector2 = spawnPosition

        /* Add player component to player */
        entity.add(createComponent(PlayerComponent::class.java))

        /* Add box2D physics to the player */
        entity.add(createComponent(PhysicsComponent::class.java))

        /* Add Health to player */
        entity.add(createComponent(HealthComponent::class.java))

        /* Add Movement to player */
        // TODO: add MovementComponent
        /* Add Sprite to player */
        entity.add(createComponent(SpriteComponent::class.java))

    }
    fun createNPC(spawnPosition: Vector2) {
        val entity: Entity = this.createEntity()
        val position: Vector2 = spawnPosition

        /* Add player component to player */
        entity.add(createComponent(PlayerComponent::class.java))

        /* Add box2D physics to the player */
        entity.add(createComponent(PhysicsComponent::class.java))

        /* Add Health to player */
        entity.add(createComponent(HealthComponent::class.java))

        /* Add Movement to player */
        // TODO: add MovementComponent
        /* Add Sprite to player */
        entity.add(createComponent(SpriteComponent::class.java))

    }
}
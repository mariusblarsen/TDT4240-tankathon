package tdt4240.tankathon.game.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.ashley.entity
import ktx.ashley.with
import tdt4240.tankathon.game.UNIT_SCALE
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import tdt4240.tankathon.game.ecs.component.*


/* PooledEngine
*
* Source: https://github.com/libgdx/ashley/wiki/Efficient-Entity-Systems-with-pooling
* */
class ECSengine: PooledEngine() {
    // TODO: Initialize Box2D world

    fun createPlayer(spawnPosition: Vector2, playerTexture: Texture): Entity {
        var player: Entity = this.entity{
            with<TransformComponent>{
            position.x = V_WIDTH*0.5f
            position.y = V_HEIGHT*0.5f
            size.x = playerTexture.width * UNIT_SCALE
            size.y = playerTexture.height * UNIT_SCALE
        }
            with<SpriteComponent>{
                sprite.run{
                    setRegion(playerTexture)
                    setSize(texture.width * UNIT_SCALE, texture.height* UNIT_SCALE)
                    setOrigin(width/2, height/4)
                }
            }
            with<PlayerComponent>()

            with<VelocityComponent>()
            with<PositionComponent>{
                position.x = V_WIDTH*0.5f
                position.y = V_HEIGHT*0.5f
            }

        }

        val position: Vector2 = spawnPosition
/*
        /* Add player component to player */
        entity.add(createComponent(PlayerComponent::class.java))


        /* Add box2D physics to the player */
        //entity.add(createComponent(PhysicsComponent::class.java))

        /* Add Health to player */
        entity.add(createComponent(HealthComponent::class.java))

        /* Add Movement to player */
        // TODO: add MovementComponent
        entity.add(createComponent(TransformComponent::class.java))

        /* Add Sprite to player */
        entity.add(createComponent(SpriteComponent::class.java))

 */
    return player


    }
    fun createNPC(spawnPosition: Vector2, texture: Texture) {
        val entity: Entity = this.entity() {
            with<TransformComponent> {
                position.x = spawnPosition.x
                position.y = spawnPosition.y

                speed=1f
                direction.x= 1f
                direction.y = 1f
                size.x = texture.width * tdt4240.tankathon.game.UNIT_SCALE
                size.y = texture.height * tdt4240.tankathon.game.UNIT_SCALE
            }
            with<SpriteComponent> {
                sprite.run {
                    setRegion(texture)
                    setSize(texture.width * tdt4240.tankathon.game.UNIT_SCALE, texture.height * tdt4240.tankathon.game.UNIT_SCALE)
                    setOrigin(width / 2, height / 4)
                }
            }
            with<AIComponent>()

            with<VelocityComponent>{
                direction.x=1f
                direction.y=0f
                speed = 1f
            }
            with<PositionComponent> {
                position.x = spawnPosition.x
                position.y = spawnPosition.y
            }


        }
    }

    /* Adds a bullet with texture, spawn point and velocity */
    fun addBullet(
            texture: Texture,
            spawnPosition: Vector3
    ): Entity {

        return entity {
            with<SpriteComponent> {
                setTexture(texture, spawnPosition)
            }
            with<TransformComponent>  {
                position = spawnPosition
            }
            with<BulletComponent>()
            with<VelocityComponent>() {
                speed = 0.2f
            }
            with<PositionComponent>() {
                spawnPosition
            }
        }
    }
}
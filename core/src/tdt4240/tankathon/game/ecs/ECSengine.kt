package tdt4240.tankathon.game.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.ashley.entity
import ktx.ashley.with
import sun.corba.EncapsInputStreamFactory
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
      return entity{
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

            with<VelocityComponent>{
                direction.set(0f,0f,0f)
                speed = 2f
            }
            with<PositionComponent>{
                position.x = V_WIDTH*0.5f
                position.y = V_HEIGHT*0.5f
            }
          with<HealthComponent>(){
              health = 3f
          }
        }
    }
    fun createNPC(spawnPosition: Vector2, texture: Texture,  enemiesIn: List<Entity>) : Entity {
        return entity {
            with<TransformComponent> {
                position.x = spawnPosition.x
                position.y = spawnPosition.y

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
            with<AIComponent>{
                enemies = enemiesIn
            }

            with<VelocityComponent>{
                direction.set(1f, 1f,0f)
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
            spawnPosition: Vector3,
            fireDirection: Vector3
    ): Entity {

        return entity {
            with<SpriteComponent> {
                setTexture(texture, spawnPosition)
            }
            with<TransformComponent>  {
                //position = spawnPosition
            }
            with<BulletComponent>()
            with<VelocityComponent>() {
                direction.set(fireDirection)
                speed = 4f
            }
            with<PositionComponent>() {
                position.set(spawnPosition)
            }
        }
    }
}
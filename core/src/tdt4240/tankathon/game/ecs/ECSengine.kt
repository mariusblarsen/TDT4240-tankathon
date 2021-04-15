package tdt4240.tankathon.game.ecs

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.objects.PolylineMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.math.Polyline
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Null
import ktx.ashley.entity
import ktx.ashley.with
import ktx.log.Logger
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.*
import tdt4240.tankathon.game.ecs.component.*
import tdt4240.tankathon.game.ecs.system.MovementSystem

private val LOG: Logger = logger<ECSengine>()
/* PooledEngine
*
* Source: https://github.com/libgdx/ashley/wiki/Efficient-Entity-Systems-with-pooling
* */
class ECSengine: PooledEngine() {
    val players = mutableListOf<Entity>()


    fun createPlayer(playerTexture: Texture, spawnPoint: Vector2): Entity {
        var player = entity{
            with<TransformComponent> ()
            with<SpriteComponent>{
                sprite.run{
                    setRegion(playerTexture)
                    setSize(texture.width * UNIT_SCALE, texture.height* UNIT_SCALE)
                    setOrigin(width/2, height/4)
                }
            }
            with<PlayerComponent>()
            with<VelocityComponent>{
                speed = 2*3f
            }
            with<PositionComponent>{
                position.x = spawnPoint.x* MAP_SCALE
                position.y = spawnPoint.y* MAP_SCALE
            }
            with<HealthComponent>{
                health = 3f
            }
            with<CanonComponent>()
            with<PhysicsComponent>{
                width = playerTexture.width * UNIT_SCALE
                height = playerTexture.width * UNIT_SCALE  // To make it quadratic
            }
        }
        players.add(player)
        return player
    }
    fun createNPC(spawnPosition: Vector2, texture: Texture, inputTeam: Int=0) : Entity {
        return entity {
            with<TransformComponent> ()
            with<SpriteComponent> {
                sprite.run {
                    setRegion(texture)
                    setSize(texture.width * UNIT_SCALE, texture.height * UNIT_SCALE)
                    setOrigin(width / 2, height / 4)
                }
            }
            with<AIComponent>{
                team = inputTeam
            }

            with<VelocityComponent>{
                speed = 1f
            }
            with<PositionComponent> {
                position.x = spawnPosition.x * MAP_SCALE
                position.y = spawnPosition.y * MAP_SCALE
            }
            with<PhysicsComponent>{
                width = texture.width * UNIT_SCALE
                height = texture.width * UNIT_SCALE  // To make it quadratic
            }
        }
    }

    /* Adds a bullet with texture, spawn point and velocity */
    fun addBullet(
            texture: Texture,
            spawnPosition: Vector3,
            fireDirection: Vector2,
    ): Entity {
        return this.entity {
            with<SpriteComponent> {
                setTexture(texture, Vector2(0f, 0f))
            }
            with<TransformComponent>  {
                rotationDeg = fireDirection.angleDeg()-90
            }
            with<BulletComponent>()
            with<VelocityComponent> {
                direction = Vector3(fireDirection, 0f)
                speed = 9f
            }
            with<PositionComponent> {
                position = spawnPosition
            }
        }
    }

    fun addMapObject(mapObject: Rectangle): Entity{
        return entity{
            with<MapObjectComponent>{
                hitbox = mapObject.apply {
                    x *= MAP_SCALE
                    y *= MAP_SCALE
                    width *= MAP_SCALE
                    height *= MAP_SCALE
                }
            }
        }
    }
    fun addMangementComponent(){
    entity{
        with<ManagementComponent>{}
    }
    }

}
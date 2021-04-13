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
import ktx.ashley.entity
import ktx.ashley.with
import tdt4240.tankathon.game.*
import tdt4240.tankathon.game.ecs.component.*


/* PooledEngine
*
* Source: https://github.com/libgdx/ashley/wiki/Efficient-Entity-Systems-with-pooling
* */
class ECSengine: PooledEngine() {
    fun createPlayer(playerTexture: Texture, spawnPoint: Vector2): Entity {
        return this.entity{
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
                speed = 2f
            }
            with<PositionComponent>{
                position.x = spawnPoint.x* MAP_SCALE
                position.y = spawnPoint.y* MAP_SCALE
            }
            with<HealthComponent>{
                health = 3f
            }
            with<CanonComponent>()
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
                position.x = spawnPosition.x * MAP_SCALE
                position.y = spawnPosition.y * MAP_SCALE
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
    fun setBackground(backgroundTexture: Texture) : Entity{
        return this.entity {
            with<SpriteComponent>{
                setTexture(backgroundTexture, Vector2(0f, 0f))
            }
            with<PositionComponent>()
        }
    }

    fun addMapObject(mapObject: Rectangle): Entity{
        val corners = FloatArray(10)
        // Bottom left
        corners[0] = 0f
        corners[1] = 0f

        // Top left
        corners[2] = 0f
        corners[3] = mapObject.height * MAP_SCALE

        // Top right
        corners[4] = mapObject.width * MAP_SCALE
        corners[5] = mapObject.height * MAP_SCALE

        // Bottom right
        corners[6] = mapObject.width * MAP_SCALE
        corners[7] = 0f

        // Bottom left (again)
        corners[8] = 0f
        corners[9] = 0f

        return this.entity{
            with<MapObjectComponent>{
                startPosition = Vector2(mapObject.x, mapObject.y)
                vertices = corners
            }
        }
    }
    fun addMapObject(mapObject: Polyline): Entity{
        return this.entity{
            with<MapObjectComponent>{
                startPosition = Vector2(mapObject.x * MAP_SCALE, mapObject.y * MAP_SCALE)
                vertices = mapObject.vertices
            }
        }
    }
}
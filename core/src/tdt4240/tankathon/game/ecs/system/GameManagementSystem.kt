package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.EllipseMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*
import tdt4240.tankathon.game.screens.GameScreen

private val NPCTexture = Texture(Gdx.files.internal("tank.png"))//TODO add suitable texture for NPC
private val LOG = logger<GameManagementSystem>()

//Takes care of adding NPCs to the game
class GameManagementSystem(private var engine: ECSengine, private var renderer: OrthogonalTiledMapRenderer)
    : IteratingSystem(
        allOf(ManagementComponent::class).get()
)
{
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val man = entity[ManagementComponent.mapper]
        require(man != null){ "Entity |entity| must have a MangagementComponent. entity=$entity"}
        man.countDown -=deltaTime

        if (man.countDown>0 || man.remaingNumberOfWaves<0){
            return
         }
        LOG.info{"Running"  }

        for (i in 0 .. man.numberOfNPCInWave){
            engine.createNPC(parseNpcSpawnpoint(renderer.map), NPCTexture)
            LOG.info {i.toString()  }
        }
        man.remaingNumberOfWaves-=1
        man.countDown=man.deltaTimeWaves
    }
    //Returns coordinates within one random NPCSpawn of type rectangle or eclipse. If there are no areas return (1,1)
    private fun parseNpcSpawnpoint(tiledMap: TiledMap) : Vector2 {
        val spawnLayer: MapLayer = tiledMap.layers.get("spawnpoint")
        val spawnObjects = spawnLayer.objects ?: return Vector2(1f, 1f)

        val areas = mutableListOf<MapObject>()
        for (spawn: MapObject in spawnObjects) {
            if (spawn.name == "NPCSpawn") {
                areas.add(spawn)
            }
        }
        if (areas.isEmpty()){
            return Vector2(1f, 1f)
        }

        val area = areas[(0 until areas.size).random()]

        if (area is RectangleMapObject) {
            return Vector2(area.rectangle.x + Math.random().toFloat() * area.rectangle.width, area.rectangle.y + Math.random().toFloat() * area.rectangle.height)
        } else if (area is EllipseMapObject) {
            // TODO: Random point wihtin ellipse or just multiple single points
            return Vector2(area.ellipse.x, area.ellipse.y)
        }

        return Vector2(1f, 1f)
    }

    }





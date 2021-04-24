package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*

private val LOG = logger<NPCWaveSystem>()

/**
 * Adds NPCs to the game for each wave.
 */
class NPCWaveSystem(private val game: TankathonGame, private var renderer: OrthogonalTiledMapRenderer)
    : IteratingSystem(
        allOf(NPCWaveComponent::class).get()
)
{
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val wave = entity[NPCWaveComponent.mapper]
        require(wave != null){ "Entity |entity| must have a MangagementComponent. entity=$entity"}
        wave.countDown -=deltaTime

        if (wave.countDown>0 || wave.remaingNumberOfWaves<0){
            return
         }

        for (i in 0 .. wave.numberOfNPCInWave){
            (engine as ECSengine).createNPC(parseNpcSpawnpoint(renderer.map), game.gameManager.assetManager.get("enemy.png"))
        }
        wave.remaingNumberOfWaves -= 1
        wave.countDown = wave.deltaTimeWaves
    }

    /**
     * Returns coordinates within one random NPCSpawn of type rectangle.
     * If there are no areas, return (1,1)
     * */
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
            return Vector2(
                    area.rectangle.x + random(area.rectangle.width),
                    area.rectangle.y + random(area.rectangle.height))
        }

        return Vector2(1f, 1f)
    }

    }





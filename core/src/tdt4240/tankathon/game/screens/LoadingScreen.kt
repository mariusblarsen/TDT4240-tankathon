package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame

private val LOG = logger<LoadingScreen>()

class LoadingScreen(game: TankathonGame) : AbstractScreen(game){
    override fun show(){
        menuStage.clear()
        game.assetManager.load("map/tilemap.tmx", TiledMap::class.java)
        game.assetManager.finishLoading()
        renderer.map = game.assetManager.get("map/tilemap.tmx", TiledMap::class.java)
        parseCollision(renderer.map)

        game.assetManager.load("tank.png", Texture::class.java)
        game.assetManager.load("enemy.png", Texture::class.java)
        game.assetManager.finishLoading()

        val playerSpawnPoint = parsePlayerSpawnpoint(renderer.map)
        /* Add entities */
        engine.createPlayer(game.assetManager.get("tank.png"), playerSpawnPoint)
        game.setScreen<GameScreen>()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    private fun parseCollision(tiledMap: TiledMap){
        val collisionLayer: MapLayer = tiledMap.layers.get("collision")
        val collisionObjects = collisionLayer.objects
        if (collisionObjects == null) {
            LOG.info { "No collisionObjects detected." }
            return
        }
        for (mapObject: MapObject in collisionObjects){
            if (mapObject is RectangleMapObject){
                game.engine.addMapObject(mapObject.rectangle)
            }
            else {
                LOG.info { "MapObject not supported!" }
            }
        }
    }
    private fun parsePlayerSpawnpoint(tiledMap: TiledMap) : Vector2 {
        val spawnLayer: MapLayer = tiledMap.layers.get("spawnpoint")
        val spawnObjects = spawnLayer.objects ?: return Vector2(1f, 1f)

        for (spawn : MapObject in spawnObjects){
            if (spawn is RectangleMapObject){
                if (spawn.name == "playerSpawn"){
                    return Vector2(spawn.rectangle.x, spawn.rectangle.y)
                }
            }
        }
        return Vector2(1f, 1f)
    }


}
package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.EllipseMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import ktx.ashley.entity
import ktx.ashley.with
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.component.ManagementComponent
import tdt4240.tankathon.game.ecs.component.MapObjectComponent

private val LOG = logger<GameScreen>()

class GameScreen(game: TankathonGame) : AbstractScreen(game){
    private val playerTexture = Texture(Gdx.files.internal("tank.png"))
    private val backgroundTexture = Texture(Gdx.files.internal("map.png"))
    private val NPCTexture = Texture(Gdx.files.internal("tank.png"))//TODO add suitable texture for NPC

    /* Box2D */
    private val bodyDef: BodyDef = BodyDef()
    private val fixtureDef: FixtureDef = FixtureDef()

    override fun show() {
        /* Loading can be moved to a loadingscreen,
        with assetManager.progress() showing progress.
         Would not need finishLoading then.*/
        game.assetManager.load("map/tilemap.tmx", TiledMap::class.java)
        game.assetManager.finishLoading()
        renderer.map = game.assetManager.get("map/tilemap.tmx", TiledMap::class.java)
        parseCollision(renderer.map)
        val playerSpawnPoint = parsePlayerSpawnpoint(renderer.map)
        val npcSpawnPoint = parseNpcSpawnpoint(renderer.map)
        /* Add entities */
        val player = engine.createPlayer(playerTexture, playerSpawnPoint)

        //Singleton
        engine.addMangementComponent()

        //val NPC = engine.createNPC(npcSpawnPoint, NPCTexture)
    }


    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        playerTexture.dispose()
        backgroundTexture.dispose()
    }

    private fun parseCollision(tiledMap: TiledMap){
        // TODO: Move to a loadscreen
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

    private fun parsePlayerSpawnpoint(tiledMap: TiledMap) : Vector2{
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
    private fun parseNpcSpawnpoint(tiledMap: TiledMap) : Vector2{
        val spawnLayer: MapLayer = tiledMap.layers.get("spawnpoint")
        val spawnObjects = spawnLayer.objects
        if (spawnObjects == null) {
            LOG.info { "No spawnObjects detected." }
            return Vector2(1f, 1f)
        }
        for (spawn : MapObject in spawnObjects){
            if (spawn.name == "npcSpawn"){
                if (spawn is RectangleMapObject){
                    return Vector2(spawn.rectangle.x, spawn.rectangle.y)
                } else if (spawn is EllipseMapObject) {
                    // TODO: Random point wihtin ellipse or just multiple single points
                    return Vector2(spawn.ellipse.x, spawn.ellipse.y)
                }
            }
        }
        return Vector2(1f, 1f)
    }
}
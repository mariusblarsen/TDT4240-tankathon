package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.math.Vector2
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame

private val LOG = logger<GameScreen>()

class GameScreen(game: TankathonGame) : AbstractScreen(game){
    private val playerTexture = Texture(Gdx.files.internal("tank.png"))
    private val backgroundTexture = Texture(Gdx.files.internal("map.png"))
    private val NPCTexture = Texture(Gdx.files.internal("tank.png"))//TODO add suitable texture for NPC

    /* Add entities */
    private val player = engine.createPlayer(playerTexture)
    private val NPC = engine.createNPC(Vector2(10f,0f), NPCTexture, listOf(player))

    override fun show() {
        /* Loading can be moved to a loadingscreen,
        with assetManager.progress() showing progress.
         Would not need finishLoading then.*/
        game.assetManager.load("map/tilemap.tmx", TiledMap::class.java)
        game.assetManager.finishLoading()
        renderer.map = game.assetManager.get("map/tilemap.tmx", TiledMap::class.java)
    }


    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        playerTexture.dispose()
        backgroundTexture.dispose()
        game.tiledMap.dispose()
    }
}
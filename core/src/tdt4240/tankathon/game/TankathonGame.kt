package tdt4240.tankathon.game

import com.badlogic.gdx.Application.LOG_INFO
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.log.Logger
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.system.DamageSystem
import tdt4240.tankathon.game.ecs.system.FireSystem
import tdt4240.tankathon.game.ecs.system.PlayerInputSystem
import tdt4240.tankathon.game.ecs.system.RenderSystem
import tdt4240.tankathon.game.ecs.system.*
import tdt4240.tankathon.game.screens.*

const val V_WIDTH_PIXELS = 480  // TODO: Real value
const val V_HEIGHT_PIXELS = 270  // TODO: Real value

const val MAP_SCALE = 1/8f
const val V_WIDTH = 16  // TODO: Real value
const val V_HEIGHT = 9  // TODO: Real value
const val UNIT_SCALE = 1/64f  // TODO: May be too much scaling for smaller textures
private val LOG: Logger = logger<TankathonGame>()


class TankathonGame : KtxGame<AbstractScreen>() {

    val assetManager: AssetManager by lazy { AssetManager().apply {
        setLoader(TiledMap::class.java, TmxMapLoader(fileHandleResolver))
    } }

    val gameCamera: OrthographicCamera by lazy { OrthographicCamera() }
    val batch: Batch by lazy { SpriteBatch() }
    val renderer: OrthogonalTiledMapRenderer by lazy { OrthogonalTiledMapRenderer(TiledMap(), MAP_SCALE, batch) }
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat(), gameCamera)
    val UIViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())

    val engine: ECSengine by lazy {
        ECSengine().apply{
            addSystem(FireSystem(this))
            addSystem(PlayerInputSystem(gameViewport, this, FireSystem(this)))
            addSystem(RenderSystem(
                    batch,
                    gameViewport,
                    renderer,
                    gameCamera))
            addSystem(DamageSystem(this))
            addSystem(AIsystem(this))
            addSystem(MovementSystem())
            addSystem(RemoveSystem())
            addSystem(GameManagementSystem(this@TankathonGame, renderer))
            addSystem(HealthSystem(this@TankathonGame))
        }
    }

    override fun create() {
        Gdx.app.logLevel = LOG_INFO
        addScreen(MenuScreen(this))
        addScreen(LoadingScreen(this))
        addScreen(GameScreen(this))
        addScreen(ScoreBoardScreen(this))
        addScreen((GameOverScreen( this)))
        addScreen(SettingsScreen(this))
        setScreen<GameOverScreen>()
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
        batch.dispose()
    }
}
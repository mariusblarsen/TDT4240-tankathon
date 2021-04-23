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


class TankathonGame(IF: FirebaseInterface) : KtxGame<AbstractScreen>() {

    val FBIF = IF

    val assetManager: AssetManager by lazy { AssetManager().apply {
        setLoader(TiledMap::class.java, TmxMapLoader(fileHandleResolver))
    } }
    val gameManager: GameManager = GameManager(this, assetManager)
    val gameCamera: OrthographicCamera by lazy { OrthographicCamera() }
    val batch: Batch by lazy { SpriteBatch() }
    val renderer: OrthogonalTiledMapRenderer by lazy { OrthogonalTiledMapRenderer(TiledMap(), MAP_SCALE, batch) }
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat(), gameCamera)
    val UIViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())

    val engine: ECSengine by lazy {
        ECSengine(gameManager).apply{
            addSystem(FireSystem(this))
            addSystem(PlayerInputSystem(gameViewport))
            addSystem(RenderSystem(
                    batch,
                    gameViewport,
                    renderer,
                    gameCamera))
            addSystem(DamageSystem(this))
            addSystem(AIsystem())
            addSystem(MovementSystem())
            addSystem(RemoveSystem())
            addSystem(GameManagementSystem(this@TankathonGame, renderer))
            addSystem(HealthSystem(this@TankathonGame))
            addSystem(ScoreSystem(this@TankathonGame, this))
        }
    }

    override fun create() {
        Gdx.app.logLevel = LOG_INFO
        addScreen(MenuScreen(gameManager))
        addScreen(LoadingScreen(gameManager))
        addScreen(GameScreen(gameManager))
        addScreen(ScoreBoardScreen(gameManager))
        addScreen((GameOverScreen( gameManager)))
        addScreen(SettingsScreen(gameManager))
        addScreen(SelectionScreen(gameManager))
        setScreen<MenuScreen>()
    }

    override fun dispose() {
        super.dispose()
        assetManager.dispose()
        batch.dispose()
    }

    fun sendScore(name: String, score: Int) {
        FBIF.sendScore(name, score)
    }

    fun getTop10(): HashMap<String, Int> {
        return FBIF.getTop10()
    }
}
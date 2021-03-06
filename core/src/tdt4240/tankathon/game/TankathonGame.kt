package tdt4240.tankathon.game

import com.badlogic.gdx.Application.LOG_INFO
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.log.Logger
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.system.*
import tdt4240.tankathon.game.gamescreens.AbstractScreen
import tdt4240.tankathon.game.gamescreens.GameScreen
import tdt4240.tankathon.game.gamescreens.LoadingScreen
import tdt4240.tankathon.game.menuscreens.*

const val V_WIDTH_PIXELS = 480
const val V_HEIGHT_PIXELS = 270

const val MAP_SCALE = 1/8f
const val V_WIDTH = 16
const val V_HEIGHT = 9
const val UNIT_SCALE = 1/64f
private val LOG: Logger = logger<TankathonGame>()


class TankathonGame(IF: FirebaseInterface) : KtxGame<AbstractScreen>() {

    val FBIF = IF
    val gameManager: GameManager = GameManager()
    val gameCamera: OrthographicCamera by lazy { OrthographicCamera() }
    val batch: Batch by lazy { SpriteBatch() }
    val renderer: OrthogonalTiledMapRenderer by lazy { OrthogonalTiledMapRenderer(TiledMap(), MAP_SCALE, batch) }
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat(), gameCamera)
    val UIViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())

    val engine: ECSengine by lazy {
        ECSengine().apply{
            addSystem(PlayerInputSystem(gameViewport))
            addSystem(RenderSystem(
                    batch,
                    gameViewport,
                    renderer,
                    gameCamera))
            addSystem(DamageSystem())
            addSystem(AIsystem())
            addSystem(MovementSystem())
            addSystem(RemoveSystem())
            addSystem(NPCWaveSystem(this@TankathonGame, renderer))
            addSystem(HealthSystem(this@TankathonGame))
            addSystem(ScoreSystem())
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
        addScreen(SelectionScreen(this))
        setScreen<MenuScreen>()
    }

    override fun dispose() {
        super.dispose()
        gameManager.dispose()
        batch.dispose()
    }

    fun getInterface(): FirebaseInterface {
        return FBIF
    }
}
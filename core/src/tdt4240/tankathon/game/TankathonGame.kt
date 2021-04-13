package tdt4240.tankathon.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_INFO
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
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
import tdt4240.tankathon.game.screens.AbstractScreen
import tdt4240.tankathon.game.screens.GameScreen
import tdt4240.tankathon.game.screens.MenuScreen

const val V_WIDTH_PIXELS = 480  // TODO: Real value
const val V_HEIGHT_PIXELS = 270  // TODO: Real value

const val V_WIDTH = 16  // TODO: Real value
const val V_HEIGHT = 9  // TODO: Real value
const val UNIT_SCALE = 1/64f  // TODO: May be too much scaling for smaller textures
private val LOG: Logger = logger<TankathonGame>()


class TankathonGame : KtxGame<AbstractScreen>() {
    val batch: Batch by lazy { SpriteBatch() }
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat())
    val engine: ECSengine by lazy {
        ECSengine().apply{
            addSystem(PlayerInputSystem(gameViewport, this))
            addSystem(RenderSystem(
                    batch,
                    gameViewport))
            addSystem(FireSystem())
            addSystem(DamageSystem(this))
        }
    }
    override fun create() {
        Gdx.app.logLevel = LOG_INFO
        LOG.info { "Create game instance" }
        addScreen(MenuScreen(this))
        addScreen(GameScreen(this))
        setScreen<MenuScreen>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
    }
}
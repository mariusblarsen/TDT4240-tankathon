package tdt4240.tankathon.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import tdt4240.tankathon.game.ecs.system.RenderSystem
import tdt4240.tankathon.game.screens.MenuScreen

const val V_WIDTH = 9  // TODO: Real value
const val V_HEIGHT = 16  // TODO: Real value

class TankathonGame : KtxGame<KtxScreen>() {
    var batch: Batch? = null
    var img: Texture? = null
    /*
    val gameViewport = FitViewport(V_WIDTH.toFloat(), V_HEIGHT.toFloat())
    val engine: PooledEngine by lazy {
        PooledEngine().apply{
            addSystem(RenderSystem(batch!!, gameViewport))
        }
    }*/
    override fun create() {
        batch = SpriteBatch()
        //addScreen(MenuScreen(this@TankathonGame))
        //setScreen<MenuScreen>()
        img = Texture("badlogic.jpg")
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch?.begin()
        batch?.draw(img, 0f, 0f)
        batch?.end()
    }

    override fun dispose() {
        batch?.dispose()
        img?.dispose()
    }
}
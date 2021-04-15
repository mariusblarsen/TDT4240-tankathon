package tdt4240.tankathon.game.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.ECSengine

abstract class AbstractScreen(
        val game: TankathonGame,
        val batch: Batch = game.batch,
        val engine: ECSengine = game.engine,
        val gameViewport: Viewport = game.gameViewport,
        val renderer: OrthogonalTiledMapRenderer = game.renderer,
        val menuStage : Stage = Stage(game.UIViewport)

) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}

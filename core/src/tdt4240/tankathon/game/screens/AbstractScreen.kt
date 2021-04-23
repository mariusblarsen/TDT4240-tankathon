package tdt4240.tankathon.game.screens

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen
import tdt4240.tankathon.game.GameManager
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.ECSengine

abstract class AbstractScreen(
        val gameManager: GameManager,
        val assetManager: AssetManager = gameManager.assetManager,
        val game: TankathonGame = gameManager.game,
        val batch: Batch = gameManager.game.batch,
        val engine: ECSengine = gameManager.game.engine,
        val gameViewport: Viewport = gameManager.game.gameViewport,
        val renderer: OrthogonalTiledMapRenderer = gameManager.game.renderer,
        val menuStage: Stage = Stage(gameManager.game.UIViewport)

) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}

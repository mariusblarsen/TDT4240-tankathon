package tdt4240.tankathon.game.gamescreens

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
        val game: TankathonGame,
        val gameManager: GameManager = game.gameManager,
        val assetManager: AssetManager = gameManager.assetManager,
        val batch: Batch = game.batch,
        val engine: ECSengine = game.engine,
        val gameViewport: Viewport = game.gameViewport,
        val renderer: OrthogonalTiledMapRenderer = game.renderer,
        val menuStage: Stage = Stage(game.UIViewport)

) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}

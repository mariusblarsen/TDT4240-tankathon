package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.app.KtxScreen
import ktx.graphics.use
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame

private val LOG = logger<MenuScreen>()


class MenuScreen(game: TankathonGame) : AbstractScreen(game) {
    val font : BitmapFont = BitmapFont()
    override fun show() {
        LOG.info { "Menu Screen" }
    }

    override fun render(delta: Float) {
        batch.use {
            font.draw(it, "Menu\nPress any key to start game", 50f, 100f)
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            game.setScreen<GameScreen>()
        }
    }
}
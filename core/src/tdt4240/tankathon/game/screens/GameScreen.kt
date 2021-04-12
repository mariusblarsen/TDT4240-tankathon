package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import ktx.ashley.entity
import ktx.ashley.with
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.UNIT_SCALE
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.PositionComponent

import tdt4240.tankathon.game.ecs.component.SpriteComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent
import javax.swing.text.html.parser.Entity

private val LOG = logger<GameScreen>()

class GameScreen(game: TankathonGame) : AbstractScreen(game){
    private val playerTexture = Texture(Gdx.files.internal("tank.png"))
    private val backgroundTexture = Texture(Gdx.files.internal("map.png"))
    private val NPCTexture = Texture(Gdx.files.internal("tank.png"))//TODO add suitable texture for NPC

    private val NPC = engine.createNPC(Vector2(10f,0f), NPCTexture, listOf(player))

    /* Add entities */
    private val background = engine.setBackground(backgroundTexture)
    private val player = engine.createPlayer(playerTexture)

    override fun show() {
        LOG.info { "Game Screen" }
    }


    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        playerTexture.dispose()
        backgroundTexture.dispose()
    }
}
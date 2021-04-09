package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import ktx.ashley.entity
import ktx.ashley.with
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.UNIT_SCALE
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH
import tdt4240.tankathon.game.ecs.component.PlayerComponent

import tdt4240.tankathon.game.ecs.component.SpriteComponent
import tdt4240.tankathon.game.ecs.component.TransformComponent

private val LOG = logger<GameScreen>()

class GameScreen(game: TankathonGame) : AbstractScreen(game){
    private val playerTexture = Texture(Gdx.files.internal("tank.png"))

    private val player = engine.entity {
        with<TransformComponent>{
            position.x = V_WIDTH*0.5f
            position.y = V_HEIGHT*0.5f
            size.x = playerTexture.width * UNIT_SCALE
            size.y = playerTexture.height * UNIT_SCALE
        }
        with<SpriteComponent>{
            sprite.run{
                setRegion(playerTexture)
                setSize(texture.width * UNIT_SCALE, texture.height* UNIT_SCALE)
                setOrigin(width/2, height/4)
            }
        }
        with<PlayerComponent>()
    }

    override fun show() {
        LOG.info { "Game Screen" }
        /* Can be uncommented to add multiple "players"
         * TODO: To be removed.
        repeat(10){
            engine.entity {
                with<TransformComponent>{
                    position.set(MathUtils.random(0f, 16f), MathUtils.random(0f, 9f), 0f)
                    size.x = playerTexture.width * UNIT_SCALE
                    size.y = playerTexture.height * UNIT_SCALE
                }
                with<SpriteComponent>{
                    sprite.run{
                        setRegion(playerTexture)
                        setSize(texture.width * UNIT_SCALE, texture.height* UNIT_SCALE)
                        setOriginCenter()
                    }
                }
                with<PlayerComponent>()
            }
        }*/
    }


    override fun render(delta: Float) {
        engine.update(delta)
    }

    override fun dispose() {
        playerTexture.dispose()
    }
}
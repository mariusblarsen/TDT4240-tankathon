package tdt4240.tankathon.game.gamescreens

import ktx.log.logger
import tdt4240.tankathon.game.GameManager
import tdt4240.tankathon.game.TankathonGame

private val LOG = logger<GameScreen>()

class GameScreen(game: TankathonGame) : AbstractScreen(game){
    override fun show() {
        //Singleton
        engine.addWaves()
    }


    override fun render(delta: Float) {
        menuStage.clear()
        engine.update(delta)
    }






}
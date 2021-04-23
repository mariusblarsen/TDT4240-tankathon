package tdt4240.tankathon.game.screens

import ktx.log.logger
import tdt4240.tankathon.game.GameManager

private val LOG = logger<GameScreen>()

class GameScreen(gameManager: GameManager) : AbstractScreen(gameManager){
    override fun show() {
        //Singleton
        engine.addMangementComponent()
    }


    override fun render(delta: Float) {
        menuStage.clear()
        engine.update(delta)

    }






}
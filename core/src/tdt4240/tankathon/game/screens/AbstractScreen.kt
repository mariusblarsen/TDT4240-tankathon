package tdt4240.tankathon.game.screens

import com.badlogic.gdx.graphics.g2d.Batch
import ktx.app.KtxScreen
import tdt4240.tankathon.game.TankathonGame

abstract class AbstractScreen(
        val game: TankathonGame,
        val batch: Batch = game.batch
) : KtxScreen
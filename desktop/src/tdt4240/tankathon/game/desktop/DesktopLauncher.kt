package tdt4240.tankathon.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.V_HEIGHT
import tdt4240.tankathon.game.V_WIDTH

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = V_HEIGHT*32
        config.width = V_WIDTH*32
        LwjglApplication(TankathonGame(), config)
    }
}
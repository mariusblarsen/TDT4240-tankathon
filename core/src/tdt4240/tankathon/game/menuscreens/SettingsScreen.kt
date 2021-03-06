package tdt4240.tankathon.game.menuscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.V_HEIGHT_PIXELS
import tdt4240.tankathon.game.V_WIDTH_PIXELS

private val LOG = logger<SettingsScreen>()

class SettingsScreen(game: TankathonGame) : AbstractUI(game) {
    var backTextButton : TextButton

    override fun show() {
        LOG.info { "Settings" }
        Gdx.input.inputProcessor = menuStage
    }

    init {
        initUI() //must be run before abstractUI class can be used

        topLabel?.setText("Settings")
        topLabel?.setAlignment(Align.center)


        backTextButton = TextButton("back", uiSkin)
        backTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<MenuScreen>()
            }
        })


        addButtonToTable()
        addActorsToStage()
        touchPos = Vector3()
    }

    private fun addButtonToTable(){
        uiTable.setDebug(false)
        uiTable.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        uiTable.setPosition(V_WIDTH_PIXELS *0.15f, V_HEIGHT_PIXELS *0.15f)

        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(topLabel).fillX
        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(backTextButton).fillX


    }

    override fun dispose() {
        uiFont.dispose()
        uiSkin.dispose()
        menuStage.dispose()
        buttonAtlas.dispose()
    }

}
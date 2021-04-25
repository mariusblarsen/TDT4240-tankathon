package tdt4240.tankathon.game.menuscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.*


private val LOG = logger<MenuScreen>()

class MenuScreen(game: TankathonGame) : AbstractUI(game) {
    //ui elements


    //interaction elements
    var startTextButton : TextButton
    var settingsTextButton : TextButton
    var scoreboardTextButton : TextButton
    var exitTextButton : TextButton

    override fun show() {
        LOG.info { "Menu Screen" }
        menuStage.clear()
        Gdx.input.inputProcessor = menuStage
        Gdx.graphics.setTitle("main menu")
        addActorsToStage()
    }

    init {
        initUI()
        //interaction-elements
        topLabel?.setText("main menu")

        startTextButton = TextButton("start", uiSkin)
        startTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                menuStage.clear()
                game.setScreen<SelectionScreen>()
            }
        })

        scoreboardTextButton = TextButton("scoreboard", uiSkin)
        scoreboardTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<ScoreBoardScreen>()
            }
        })

        settingsTextButton = TextButton("settings", uiSkin)
        settingsTextButton.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    game.setScreen<SettingsScreen>()
                }
            })

        exitTextButton = TextButton("exit", uiSkin)
        exitTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Gdx.app.exit()
            }
        })


        //creates table
        addButtonToTable()
        addActorsToStage()
        touchPos = Vector3()

    }


    private fun addButtonToTable(){
        uiTable.setDebug(false)
        uiTable.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        uiTable.setPosition(V_WIDTH_PIXELS.toFloat()*0.15f, V_HEIGHT_PIXELS.toFloat()*0.15f)


        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(topLabel).fillX
        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(startTextButton).fillX
        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(scoreboardTextButton).fillX
        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(settingsTextButton).fillX
        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(exitTextButton).fillX
    }

    override fun dispose() {
        uiDispose()
        menuStage.dispose()
        buttonAtlas.dispose()

    }

}

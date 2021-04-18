package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import ktx.graphics.use
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
        Gdx.input.inputProcessor = menuStage
    }

    init {
        initUI()
        //interaction-elements
        topLabel?.setText("main menu")

        startTextButton = TextButton("start", uiSkin)
        startTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                menuStage.clear()
                game.setScreen<LoadingScreen>()
            }
        })

        scoreboardTextButton = TextButton("ScoreBoard", uiSkin)
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


        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(topLabel).fillX
        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(startTextButton).fillX
        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(scoreboardTextButton).fillX
        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(settingsTextButton).fillX
        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(exitTextButton).fillX
    }

    fun addActorsToStage(){
        menuStage.addActor(uiTable)
    }

    override fun render(delta: Float) {
        //tømmer skjerm og setter bakgrunn
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        update(delta)
        menuStage.draw()


        batch.use {
            val str = "mousePos x,y: "+Gdx.input.getX().toString()+","+Gdx.input.getY().toString()
            uiFont.draw(it, str, 0f, 20f)
        }
        // process user input
        /*if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
        }
         */
    }
    fun update(delta: Float) {menuStage.act(delta)}

    override fun resize(width: Int, height: Int) { }
    override fun hide() { }
    override fun pause() { }
    override fun resume() { }
    override fun dispose() {
        uiDispose()
        menuStage.dispose()
        buttonAtlas.dispose()

    }

}

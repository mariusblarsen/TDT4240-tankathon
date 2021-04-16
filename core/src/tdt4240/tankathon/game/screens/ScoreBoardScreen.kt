package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.graphics.use
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.*

private val LOG = logger<ScoreBoardScreen>()

class ScoreBoardScreen(game: TankathonGame) : AbstractUI(game){
    var scoreboardTable : Table

    //interaksjonselementer
    var backTextButton : TextButton
    var exitTextButton : TextButton



    override fun show() {
        LOG.info { "ScoreBoardScreen" }
        Gdx.input.inputProcessor = menuStage
        //lager er scoreboard table
        createScoreboardTable(6,true)

        addButtonToTable()
        addActorsToStage()
    }

    init {

        initUI()
        topLabel?.setText("score board")
        scoreboardTable = Table(uiSkin)


        backTextButton = TextButton("back", uiSkin)
        backTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                menuStage.clear()
                game.setScreen<MenuScreen>()
            }
        })

        exitTextButton = TextButton("exit", uiSkin)
        exitTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Gdx.app.exit()
            }
        })
    }

    private fun createScoreboardTable(rows:Int, demo:Boolean){
        if (demo){
            val sorted = getScores().toList().sortedBy { (_,value) -> value}.reversed().toMap()
            scoreboardTable.reset()
            var number = 1
            for ((key, value) in sorted) {
                if(number-1==rows) break
                scoreboardTable.row()
                scoreboardTable.add(number.toString()+": ")
                scoreboardTable.add(key)
                scoreboardTable.add(value.toString())
                number++
            }
        }else{
            println("*******************non demo function not yet implemented*****************")
        }
    }

    private fun addButtonToTable(){
        /**
         * adds buttons and scoreboard table to uiTable, should be called
         * after: create scoreBoardTable
         * before: addActors
         */
        uiTable.reset()
        uiTable.setDebug(false)
        uiTable.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        uiTable.setPosition(V_WIDTH_PIXELS*0.15f, V_HEIGHT_PIXELS*0.15f)

        uiTable.row().colspan(1)
        uiTable.add(topLabel).fillX

        uiTable.row().colspan(2).expandX().fillX().center()
        uiTable.add(scoreboardTable)

        uiTable.row().colspan(3).expandX().fillX();
        uiTable.add(backTextButton).fillX
        uiTable.add(exitTextButton).fillX

    }

    private fun addActorsToStage(){
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
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
        }


    }

    fun getScores(): HashMap<String, Int> {
        val scores = hashMapOf<String,Int>(
                "magnus" to 120,
                "bjorn" to 1200,
                "johan" to 900,
                "kristian" to 700,
                "oeystein" to 1299
        )
        return scores
    }


    fun update(delta: Float) {
        menuStage.act(delta)
    }
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
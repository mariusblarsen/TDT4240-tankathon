package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
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
    lateinit var scroller : ScrollPane

    //interaksjonselementer
    var backTextButton : TextButton
    var exitTextButton : TextButton



    override fun show() {
        menuStage.clear()
        LOG.info { "ScoreBoardScreen" }
        Gdx.input.inputProcessor = menuStage
        //lager er scoreboard table
        createScoreboardTable(20,true)
        scroller = ScrollPane(scoreboardTable)
        addButtonToTable()
        addActorsToStage()
    }

    init {
        initUI()
        topLabel?.setText("scoreboard")
        scoreboardTable = Table(uiSkin)

        //createButton("back",MenuScreen)

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
        val sorted = game.getTop10().toList().sortedBy { (_,value) -> value}.reversed().toMap()
        //val sorted = getScores().toList().sortedBy { (_,value) -> value}.reversed().toMap()

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

        uiTable.row().colspan(2)
        uiTable.add(topLabel).fillX

        uiTable.row().colspan(2).expandX().fillX().center()
        uiTable.add(scroller)

        uiTable.row().colspan(1).expandX().fillX();
        uiTable.add(backTextButton).fillX
        uiTable.add(exitTextButton).fillX

    }

    private fun addActorsToStage(){
        menuStage.addActor(uiTable)
    }

    override fun render(delta: Float) {
        renderUi()
        update(delta)
    }

    fun getScores(): HashMap<String, Int> {
        val scores = hashMapOf<String,Int>(
                "magnus" to 120,
                "bjorn" to 120,
                "johan" to 900,
                "kristian" to 700,
                "oeystein" to 1299,
                "magnus1" to 122,
                "bjorn1" to 1202,
                "johan1" to 902,
                "kristian1" to 702,
                "oeystein" to 1290,
                "magnus2" to 121,
                "bjorn2" to 1201,
                "johan2" to 901,
                "kristian2" to 800,
                "oeystein2" to 299
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
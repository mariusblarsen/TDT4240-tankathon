package tdt4240.tankathon.game.menuscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.V_HEIGHT_PIXELS
import tdt4240.tankathon.game.V_WIDTH_PIXELS

private val LOG = logger<ScoreBoardScreen>()

class ScoreBoardScreen(game: TankathonGame) : AbstractUI(game){
    private val FBIF = game.getInterface()
    var scoreboardTable : Table
    lateinit var scroller : ScrollPane

    //interaksjonselementer
    var backTextButton : TextButton

    override fun show() {
        menuStage.clear()
        LOG.info { "ScoreBoardScreen" }
        Gdx.input.inputProcessor = menuStage
        //lager er scoreboard table
        createScoreboardTable(100,false)
        scroller = ScrollPane(scoreboardTable)
        addButtonToTable()
        addActorsToStage()
    }

    init {
        initUI()
        topLabel?.setText("scoreboard | top 100")
        scoreboardTable = Table(uiSkin)

        backTextButton = TextButton("back", uiSkin)
        backTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                menuStage.clear()
                game.setScreen<MenuScreen>()
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
            val sorted = FBIF.getTop10().toList().sortedBy { (_,value) -> value}.reversed().toMap()
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

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(backTextButton).fillX


    }

    /**
     * For debugging in desktop launcher.
     */
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


    override fun dispose() {
        uiDispose()
        menuStage.dispose()
        buttonAtlas.dispose()
    }


}
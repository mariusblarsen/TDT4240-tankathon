package tdt4240.tankathon.game.menuscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.V_HEIGHT_PIXELS
import tdt4240.tankathon.game.V_WIDTH_PIXELS

private val LOG = logger<GameOverScreen>()

class GameOverScreen(game: TankathonGame) : AbstractUI(game) {
    companion object {
        var playerHighscore : Float = -1f
        fun getCompanionHighscore():Float { return playerHighscore }
        fun setCompanionHigscore(highscore:Float){ playerHighscore =highscore}
    }

    private val FBIF = game.getInterface()
    var enteredScoreTextField : TextField
    var enteredUsernameTextfield : TextField
    var saveHighcoreTextButton:TextButton
    var backTextButton : TextButton

    override fun show() {
        menuStage.clear()
        uiTable.clear()
        LOG.info { "GameOver" }
        Gdx.input.inputProcessor = menuStage
        Gdx.graphics.setTitle("game over")
        enteredScoreTextField.text= getCompanionHighscore().toInt().toString()
        addButtonToTable()
        addActorsToStage()
    }

    init {
        initUI() //must be run before abstractUI class can be used

        topLabel?.setText("game over")
        topLabel?.setAlignment(Align.center)

        enteredScoreTextField = TextField("",uiSkin)
        enteredUsernameTextfield = TextField("", uiSkin)

        backTextButton = TextButton("main menu", uiSkin)
        backTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<MenuScreen>()
            }
        })
        saveHighcoreTextButton = TextButton("save score", uiSkin)
        saveHighcoreTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                saveHighScoreToScoreBoard()
            }
        })
        touchPos = Vector3()
    }

    private fun getHighScore(demo: Boolean = false):Int{
        var highScore = 0
        if(demo){
            highScore =  101
        }else{
            highScore = getCompanionHighscore().toInt()
        }
        return highScore
    }

    private fun saveHighScoreToScoreBoard(){
        //TODO: lagre highscore i database og sjekke om username er gyldig
        val username = enteredUsernameTextfield.text
        //hvis man prøver å lagre uten å skirve navn returnerer man til hjemskjerm
        if (username.equals(null) || username.toString().equals("your_username") || username.equals("")){
            enteredUsernameTextfield.text="(unique username here)"
            LOG.info { "highscore not saved" }
        }else{
            LOG.info { "highscore pushed to firebase"}
            FBIF.sendScore(username,getHighScore())
            FBIF.getTop10()
            game.setScreen<MenuScreen>()
        }
    }

    private fun addButtonToTable(){

        uiTable.setDebug(false)
        uiTable.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        uiTable.setPosition(V_WIDTH_PIXELS * 0.15f, V_HEIGHT_PIXELS * 0.15f)


        uiTable.row().colspan(4).fillX().center()
        uiTable.add(topLabel)

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add("your score:")
        uiTable.add(enteredScoreTextField)

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add("username:")
        uiTable.add(enteredUsernameTextfield)

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(backTextButton)
        uiTable.add(saveHighcoreTextButton)


    }

    override fun dispose() {
        uiFont.dispose()
        uiSkin.dispose()
        menuStage.dispose()
        buttonAtlas.dispose()
    }

}
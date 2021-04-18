package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
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

    var highscoreTextField : TextField
    var enteredHighscoreTextField : TextField

    var usernameTextField : TextField
    var enteredUsernameTextfield : TextField

    var saveHighcoreTextButton:TextButton
    var backTextButton : TextButton


    override fun show() {

        LOG.info { "GameOver" }
        Gdx.input.inputProcessor = menuStage
    }

    init {
        initUI() //must be run before abstractUI class can be used

        topLabel?.setText("GameOver")
        topLabel?.setAlignment(Align.center)

        highscoreTextField = TextField("your score: ",uiSkin)
        enteredHighscoreTextField = TextField(getHighScore(true),uiSkin)

        usernameTextField = TextField("your_username: ",uiSkin)
        enteredUsernameTextfield = TextField("",uiSkin)



        backTextButton = TextButton("back", uiSkin)
        backTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<MenuScreen>()
            }
        })
        saveHighcoreTextButton = TextButton("save high score", uiSkin)
        saveHighcoreTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                saveHighScore()
            }
        })
        addButtonToTable()
        addActorsToStage()
        touchPos = Vector3()
    }


    private fun getHighScore(demo:Boolean=false):String{
        var highScore =0
        if(demo){
            highScore =  101
        }
        return highScore.toString()
    }

    private fun saveHighScore(){
        //TODO: lagre highscore i database
        var username = enteredUsernameTextfield.messageText
        println(username)
        /*
        //hvis man prøver å lagre uten å skirve navn returnerer man til hjemskjerm
        if (username.equals(null) || username.toString().equals("your_username")){
            game.setScreen<MenuScreen>()
            LOG.info { "highscore not saved" }
        }else{
            LOG.info { "highscore pushed to firebase(not yet implemented" }
        }
         */



    }

    private fun addButtonToTable(){
        uiTable.setDebug(false)
        uiTable.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        uiTable.setPosition(V_WIDTH_PIXELS *0.15f, V_HEIGHT_PIXELS *0.15f)


        uiTable.row().colspan(3).fillX().center()
        uiTable.add(topLabel).fillX

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(highscoreTextField).fillX
        uiTable.add(enteredHighscoreTextField).fillX

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(usernameTextField).fillX
        uiTable.add(enteredUsernameTextfield).fillX

        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(backTextButton).fillX
        uiTable.add(saveHighcoreTextButton).fillX


    }

    private fun addActorsToStage(){
        menuStage.addActor(uiTable)
    }


    override fun render(delta: Float) {
        renderUi()
        update(delta)
    }


    fun update(delta: Float) {
        menuStage.act(delta)
    }
    override fun resize(width: Int, height: Int) { }
    override fun hide() { }
    override fun pause() { }
    override fun resume() { }


    override fun dispose() {
        uiFont.dispose()
        uiSkin.dispose()
        menuStage.dispose()
        buttonAtlas.dispose()
    }

}
package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import ktx.graphics.use
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame


private val LOG = logger<MenuScreen>()

class MenuScreen(game: TankathonGame) : AbstractScreen(game) {
    //TODO implementer object pool
    val font : BitmapFont = BitmapFont()
    var touchPos : Vector3
    var skin : Skin = Skin()
    var mainMenuStage : Stage  = Stage()
    var buttonAtlas :  TextureAtlas

    //buttons

    lateinit var startTextButton : TextButton
    lateinit var settingsTextButton : TextButton
    lateinit var scoreboardTextButton : TextButton
    lateinit var exitTextButton : TextButton

    /*
    lateinit var buttonStartImage : Texture
    lateinit var buttonScoreImage : Texture
    lateinit var buttonSettingsImage : Texture
    */

    //størrelsen på knappene
    val buttonHeight = 10f
    val buttonWidth = 200f
    val buttonSpace = 20f

    //størrelse på skjerm
    val cameraMiddleWidth : Float
    val cameraMiddleHeight : Float

    override fun show() {
        LOG.info { "Menu Screen" }
    }

    init {
        Gdx.input.setInputProcessor(mainMenuStage);

        //TODO: opretter nytt kamera med bredde fra objectpool, kan forandre kamera til object pool om det er ortografisk
        cameraMiddleWidth = Gdx.graphics.height.toFloat()/2f
        cameraMiddleHeight = Gdx.graphics.width.toFloat()/2f
        //initRectangles()


        buttonAtlas = TextureAtlas(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.atlas"));
        skin.addRegions(buttonAtlas)
        skin.load(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.json"))

        initButtons()

        touchPos = Vector3()

        /*
        var startImage = Texture(Gdx.files.internal("buttonStart.png"))
        var SettingsImage = Texture(Gdx.files.internal("buttonSettings.png"))
        var highscoreImage = Texture(Gdx.files.internal("buttonScore.png"))
        */




    }

    override fun render(delta: Float) {
        //tømmer skjerm og setter bakgrunn
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //kan slettes tror eg
        update(delta)

        mainMenuStage.draw()



        batch.use {
            //gammel
            //drawRectangles()


        }

        // process user input
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
            //camera.unproject(touchPos)
            //bucket.x = touchPos.x - 64f/2f
        }
        /*
        if(Gdx.input.isKeyJustPressed(Input.) || Gdx.input.isTouched){
            game.setScreen<GameScreen>()
        }
         */
    }


    fun initButtons(){
        var startTextButton = TextButton("start", skin)
        var scoreboardTextButton = TextButton("ScoreBoard", skin)
        var settingsTextButton = TextButton("settings", skin)
        var exitTextButton = TextButton("exit", skin)



        startTextButton.setPosition(cameraMiddleWidth, (cameraMiddleHeight) + buttonHeight)
        scoreboardTextButton.setPosition(startTextButton.x, startTextButton.y - buttonSpace - buttonHeight)
        settingsTextButton.setPosition(startTextButton.x, scoreboardTextButton.y - buttonSpace - buttonHeight)
        exitTextButton.setPosition(startTextButton.x, scoreboardTextButton.y - buttonSpace - buttonHeight - buttonSpace - buttonHeight)

        startTextButton.setSize(buttonWidth, buttonHeight)
        scoreboardTextButton.setSize(buttonWidth, buttonHeight)
        settingsTextButton.setSize(buttonWidth, buttonHeight)
        exitTextButton.setSize(buttonWidth, buttonHeight)




        startTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<GameScreen>()
            }
        })
        exitTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Gdx.app.exit()
            }
        })

        mainMenuStage.addActor(startTextButton)
        mainMenuStage.addActor(scoreboardTextButton)
        mainMenuStage.addActor(settingsTextButton)
        mainMenuStage.addActor((exitTextButton))
    }

/*
    fun initRectangles(){
        startButton = Rectangle(
                (cameraMiddleWidth - buttonWidth/2f),
                (cameraMiddleHeight - buttonHeight/2f) + buttonSpace + buttonHeight,
                buttonWidth,
                buttonHeight)
        scoreboardButton = Rectangle(
                startButton.x,
                startButton.y - buttonSpace - buttonHeight,
                buttonWidth,
                buttonHeight)
        settingsButton = Rectangle(
                startButton.x,
                scoreboardButton.y - buttonSpace - buttonHeight,
                buttonWidth,
                buttonHeight)
        buttonStartImage = Texture(Gdx.files.internal("buttonStart.png"))
        buttonScoreImage = Texture(Gdx.files.internal("buttonScore.png"))
        buttonSettingsImage = Texture(Gdx.files.internal("buttonSettings.png"))
    }

    fun drawRectangles() {
        font.draw(batch, "Menu", cameraMiddleWidth - buttonWidth / 2f, startButton.y + buttonHeight + buttonSpace * 2f)
        //start
        batch.draw(buttonStartImage, startButton.x, startButton.y, startButton.width, startButton.height)
        font.draw(batch, "start", startButton.x, startButton.y)
        //scoreboard
        batch.draw(buttonScoreImage, scoreboardButton.x, scoreboardButton.y, scoreboardButton.width, scoreboardButton.height)
        font.draw(batch, "scoreboard", scoreboardButton.x, scoreboardButton.y)
        //settings
        batch.draw(buttonSettingsImage, settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height)
        font.draw(batch, "settings", settingsButton.x, settingsButton.y)
    }
*/
    fun update(delta: Float) {
    mainMenuStage.act(delta)
    }

    override fun resize(width: Int, height: Int) { }
    override fun hide() { }
    override fun pause() { }
    override fun resume() { }


    override fun dispose() {
    //TODO implementer kasting av alle knapper osv
        /*
        buttonStartImage.dispose()
        buttonScoreImage.dispose()
        buttonSettingsImage.dispose()
         */

    }

}

package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import ktx.graphics.use
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame


private val LOG = logger<MenuScreen>()

class MenuScreen(game: TankathonGame) : AbstractScreen(game) {
    val font : BitmapFont = BitmapFont()
    //vurder objectpool

    var startButton : Rectangle
    var settingsButton : Rectangle
    var scoreboardButton : Rectangle
    var buttonImage : Texture

    var touchPos : Vector3
    var camera : OrthographicCamera

    //størrelsen på knappene
    val buttonHeight = 20f
    val buttonWidth = 200f
    val buttonSpace = 20f


    val cameraMiddleWidth : Float
    val cameraMiddleHeight : Float


    override fun show() {
        LOG.info { "Menu Screen" }

    }

    init {
        //TODO: opretter nytt kamera med bredde fra objectpool, kan forandre kamera til object pool om det er ortografisk

        camera = OrthographicCamera(480f, 270f)


        cameraMiddleHeight = camera.viewportHeight/2f
        cameraMiddleWidth = camera.viewportWidth/2f
        println("camera height: " + cameraMiddleHeight.toString() + " width: " + cameraMiddleWidth.toString())

        //TODO: bytte ut rektangel med button
        startButton = Rectangle(
                cameraMiddleWidth - buttonWidth / 2f,
                cameraMiddleHeight + buttonHeight / 2f + buttonSpace,
                buttonWidth,
                buttonHeight)
        scoreboardButton = Rectangle(
                cameraMiddleWidth - buttonWidth / 2f,
                cameraMiddleHeight + buttonHeight / 2f,
                buttonWidth,
                buttonHeight)
        settingsButton = Rectangle(
                cameraMiddleWidth - buttonWidth / 2f,
                cameraMiddleHeight - buttonHeight / 2f - buttonSpace,
                buttonWidth,
                buttonHeight)

        //create buttons

        // temporary until we have asset manager in

        // temporary until we have asset manager in




        //touch/mus
        touchPos = Vector3()

        //button bilde
        buttonImage = Texture(Gdx.files.internal("/Users/Bjorn/progArki/TDT4240-tankathon/core/src/tdt4240/tankathon/game/screens/button.png"))
    }

    override fun render(delta: Float) {
        //TODO: gjøre så kameraet også skalerer etter skjerm.

        //batch.begin()
        //tømmer skjerm og setter bakgrunn
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        //oppdater kamera en gang per frame
        camera.update()

        batch.use {
            font.draw(it, "Menu\nPress any key to start game" + cameraMiddleWidth, 50f, 280f)

            batch.draw(buttonImage, startButton.x, startButton.y, startButton.width, startButton.height)
            font.draw(it, "Menu\nPress any key to start game" + cameraMiddleWidth, 50f, 280f)

        }

        // process user input
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
            //camera.unproject(touchPos)
            //bucket.x = touchPos.x - 64f/2f
        }









        /*
        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched){
            game.setScreen<GameScreen>()
        }

         */
    }
    override fun resize(width: Int, height: Int) { }
    override fun hide() { }
    override fun pause() { }
    override fun resume() { }


    override fun dispose() {
        buttonImage.dispose()
    }

}

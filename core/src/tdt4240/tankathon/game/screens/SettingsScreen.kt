package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
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
import ktx.graphics.use
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.V_HEIGHT_PIXELS
import tdt4240.tankathon.game.V_WIDTH_PIXELS

private val LOG = logger<SettingsScreen>()

class SettingsScreen(game: TankathonGame) : AbstractUI(game) {
    //ui elementer
    /*
    val font : BitmapFont = BitmapFont()
    var touchPos : Vector3
    var skin : Skin = Skin()
    var buttonAtlas : TextureAtlas
    var table : Table

    //interaksjonselementer
    */
    //var topLabel : Label



    var backTextButton : TextButton




    override fun show() {
        LOG.info { "Settings" }
        Gdx.input.inputProcessor = menuStage
    }

    init {
        initUI()
        //which stage that controls the input


        //ui-elements
        /*
        buttonAtlas = TextureAtlas(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.atlas"));
        skin.addRegions(buttonAtlas)
        skin.load(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.json"))
        table = Table(skin)



        //interaction-elements
        topLabel = Label("Settings", skin)
        */
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

    private fun addActorsToStage(){
        menuStage.addActor(uiTable)
        //menuStage.addActor(exitTextButton)
        //menuStage.addActor(backTextButton)
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
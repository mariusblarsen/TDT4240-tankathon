package tdt4240.tankathon.game.menuscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import ktx.graphics.use
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.gamescreens.AbstractScreen

abstract class AbstractUI(
        /**
         * should implement uiInit() and dispose()
         */
    //ui elementer
    val uiGame : TankathonGame,
    val uiFont : BitmapFont = BitmapFont(),
    var touchPos : Vector3 = Vector3(),
    var uiSkin : Skin = Skin(),
    var buttonAtlas : TextureAtlas = TextureAtlas(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.atlas")),
    var uiTable : Table = Table(uiSkin),

    //interaksjonselementer
    var topLabel : Label? = null

) : AbstractScreen(uiGame) {
    fun initUI(){
        /**
         * needs to be ran before using objects
         */
        uiSkin.addRegions(buttonAtlas)
        uiSkin.load(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.json"))
        uiTable = Table(uiSkin)

        //interaction-elements
        topLabel = Label("topLabel", uiSkin)
        topLabel?.setAlignment(Align.center)
        topLabel?.setFontScale(1.4f,1.4f)

        menuStage.draw()
        menuStage.act()

    }
    fun uiDispose(){
        uiFont.dispose()
        uiSkin.dispose()
        buttonAtlas.dispose()
        uiTable.clear()
    }
    fun renderUi(showPointerCoordinateOnScreen: Boolean =false){
        //tømmer skjerm og setter bakgrunn
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        menuStage.draw()

        if(showPointerCoordinateOnScreen){
            batch.use {
                val str = "mousePos x,y: "+Gdx.input.getX().toString()+","+Gdx.input.getY().toString()
                uiFont.draw(it, str, 0f, 20f)
            }
        }
        //ikke sikkert vi trenger denne:
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
        }
    }

    fun addActorsToStage(){
        menuStage.addActor(uiTable)
    }

    override fun render(delta: Float) {
        renderUi()
        update(delta)
    }
    fun update(delta: Float) {
        menuStage.act(delta)
    }
}
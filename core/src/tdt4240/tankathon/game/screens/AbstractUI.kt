package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.Viewport
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.ECSengine

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

    }
    fun uiDispose(){
        uiFont.dispose()
        uiSkin.dispose()
        buttonAtlas.dispose()
        uiTable.clear()
    }

}
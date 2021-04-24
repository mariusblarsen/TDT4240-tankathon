package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import ktx.graphics.use
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.*


private val LOG = logger<SelectionScreen>()

class SelectionScreen(game: TankathonGame) : AbstractUI(game) {
    //ui elements
    //interaction elements
    private var lightPlayerTextButton : TextButton
    private var heavyPlayerTextButton : TextButton
    private val lightPlayerLabel : Label by lazy { Label("", uiSkin).apply {
        setText("Fast, rapid fire with low health.")
        setAlignment(Align.center)
        setFontScale(1.4f,1.4f)
    } }
    private val heavyPlayerLabel : Label by lazy { Label("", uiSkin).apply {
        setText("Slow, heavy hitter with high health.")
        setAlignment(Align.center)
        setFontScale(1.4f,1.4f)
    } }


    override fun show() {
        LOG.info { "Menu Screen" }
        menuStage.clear()
        Gdx.input.inputProcessor = menuStage
        Gdx.graphics.setTitle("main menu")
        addActorsToStage()
    }

    init {
        initUI()
        //interaction-elements
        topLabel?.setText("Select characters")

        lightPlayerTextButton = TextButton("Lightweight", uiSkin)
        lightPlayerTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameManager.setLightCharacter()
                game.setScreen<LoadingScreen>()
                menuStage.clear()
            }
        })

        heavyPlayerTextButton = TextButton("Heavyweight", uiSkin)
        heavyPlayerTextButton.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    gameManager.setHeavyCharacter()
                    game.setScreen<LoadingScreen>()
                    menuStage.clear()
                }
            })

        //creates table
        addButtonToTable()
        addActorsToStage()
        touchPos = Vector3()

    }


    private fun addButtonToTable(){
        uiTable.setDebug(false)
        uiTable.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        uiTable.setPosition(V_WIDTH_PIXELS.toFloat()*0.15f, V_HEIGHT_PIXELS.toFloat()*0.15f)


        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(topLabel).fillX
        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(lightPlayerTextButton).fillX
        uiTable.row().colspan(2).expandX().fillX();
        uiTable.add(lightPlayerLabel).fillX
        uiTable.row().colspan(4).expandX().fillX();
        uiTable.add(heavyPlayerTextButton).fillX
        uiTable.row().colspan(4).expandX().fillX();
        uiTable.add(heavyPlayerLabel).fillX
    }

    override fun dispose() {
        uiDispose()
        menuStage.dispose()
        buttonAtlas.dispose()

    }

}

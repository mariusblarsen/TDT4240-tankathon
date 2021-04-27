package tdt4240.tankathon.game.menuscreens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.V_HEIGHT_PIXELS
import tdt4240.tankathon.game.V_WIDTH_PIXELS
import tdt4240.tankathon.game.gamescreens.LoadingScreen


private val LOG = logger<SelectionScreen>()

class SelectionScreen(game: TankathonGame) : AbstractUI(game) {
    //ui elements
    //interaction elements
    private var backTextButton : TextButton
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

        backTextButton = TextButton("Return to main menu", uiSkin)
        backTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<MenuScreen>()
            }
        })

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
        /* Back button */
        uiTable.row().colspan(4).expandX().fillX();
        uiTable.add(backTextButton).fillX
    }

    override fun dispose() {
        uiDispose()
        menuStage.dispose()
        buttonAtlas.dispose()

    }

}

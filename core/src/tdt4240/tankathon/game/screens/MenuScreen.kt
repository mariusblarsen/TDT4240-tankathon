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


private val LOG = logger<MenuScreen>()

class MenuScreen(game: TankathonGame) : AbstractScreen(game) {
    //ui elements
    val font : BitmapFont = BitmapFont()
    var touchPos : Vector3
    var skin : Skin = Skin()
    var buttonAtlas :  TextureAtlas
    var table : Table


    //interaction elements
    var topLabel : Label
    var startTextButton : TextButton
    var settingsTextButton : TextButton
    var scoreboardTextButton : TextButton
    var exitTextButton : TextButton

    override fun show() {
        LOG.info { "Menu Screen" }
        Gdx.input.inputProcessor = menuStage
    }

    init {
        //ui-elementes
        buttonAtlas = TextureAtlas(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.atlas"))
        skin.addRegions(buttonAtlas)
        skin.load(Gdx.files.internal("Neon_UI_Skin/neonui/neon-ui.json"))
        table = Table(skin)

        //interaction-elements
        topLabel = Label("Main Menu", skin)
        topLabel.setAlignment(Align.center)

        startTextButton = TextButton("start", skin)
        startTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<GameScreen>()
            }
        })

        scoreboardTextButton = TextButton("ScoreBoard", skin)
        scoreboardTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                game.setScreen<ScoreBoardScreen>()
            }
        })

        settingsTextButton = TextButton("settings", skin)
        settingsTextButton.addListener(object : ChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    game.setScreen<SettingsScreen>()
                }
            })

        exitTextButton = TextButton("exit", skin)
        exitTextButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                Gdx.app.exit()
            }
        })


        //creates table
        addButtonToTable()
        addActorsToStage()
        touchPos = Vector3()

    }


    private fun addButtonToTable(){
        table.setDebug(false)
        table.setSize(V_WIDTH_PIXELS.toFloat() * 0.7f, V_HEIGHT_PIXELS.toFloat() * 0.7f)
        table.setPosition(V_WIDTH_PIXELS.toFloat()*0.15f, V_HEIGHT_PIXELS.toFloat()*0.15f)


        table.row().colspan(3).expandX().fillX();
        table.add(topLabel).fillX
        table.row().colspan(3).expandX().fillX();
        table.add(startTextButton).fillX
        table.row().colspan(3).expandX().fillX();
        table.add(scoreboardTextButton).fillX
        table.row().colspan(3).expandX().fillX();
        table.add(settingsTextButton).fillX
        table.row().colspan(3).expandX().fillX();
        table.add(exitTextButton).fillX
    }

    fun addActorsToStage(){
        menuStage.addActor(table)
    }

    override fun render(delta: Float) {
        //tømmer skjerm og setter bakgrunn
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        update(delta)
        menuStage.draw()

        batch.use {
            val str = "mousePos x,y: "+Gdx.input.getX().toString()+","+Gdx.input.getY().toString()
            font.draw(it, str, 0f, 20f)
        }
        // process user input
        /*if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f)
        }
         */
    }

    fun printCoordinates(){
        println("\n skjermbredde: " + Gdx.graphics.width + " skjermhøyde: " + Gdx.graphics.height)
        println("tablebredde: " + table.width + " table høyde: " + table.height)
        println("table_x: " + table.x + " table_y: " + table.y)
        println("viewport bredde/høyde ikke pixel?:"+gameViewport.screenWidth+"/"+gameViewport.screenHeight)
    }

    fun update(delta: Float) {menuStage.act(delta)}


    override fun resize(width: Int, height: Int) { }
    override fun hide() { }
    override fun pause() { }
    override fun resume() { }
    override fun dispose() {
        font.dispose()
        skin.dispose()
        menuStage.dispose()
        buttonAtlas.dispose()
    }

}

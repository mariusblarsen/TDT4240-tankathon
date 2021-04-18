package tdt4240.tankathon.game.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.EllipseMapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import ktx.ashley.entity
import ktx.ashley.with
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.TankathonGame
import tdt4240.tankathon.game.ecs.component.ManagementComponent
import tdt4240.tankathon.game.ecs.component.MapObjectComponent

private val LOG = logger<GameScreen>()

class GameScreen(game: TankathonGame) : AbstractScreen(game){
    override fun show() {
        //Singleton
        engine.addMangementComponent()
    }


    override fun render(delta: Float) {
        menuStage.clear()
        engine.update(delta)

    }






}
package tdt4240.tankathon.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

abstract class Character {
    var maxHealth: Float = 0f
    var speed: Float = 0f
    var damage: Float = 0f
    var fireRate: Float = 0f
    var texture: Int = 0
}

class HeavyCharacter : Character() {
    init {
        maxHealth = 150f
        speed = 4f
        damage = 50f
        fireRate = 0.3f
        texture = 0
    }
}
class LightCharacter : Character() {
    init {
        maxHealth = 80f
        speed = 10f
        damage = 8f
        fireRate = 0.05f
        texture = 1
    }
}


class GameManager (val game: TankathonGame
) {
    val assetManager: AssetManager by lazy { AssetManager().apply {
        setLoader(TiledMap::class.java, TmxMapLoader(fileHandleResolver))
    } }
    private var character: Character = LightCharacter()
    private val playerTextures: Array<String> = arrayOf("tank.png", "guy_teal.png")

    fun getPlayer() : Character {
        return character
    }

    fun getTexturePath() : String{
        return playerTextures[character.texture]
    }

    fun setHeavyCharacter(){
        character = HeavyCharacter()
    }
    fun setLightCharacter(){
        character = LightCharacter()
    }

    fun dispose(){
        assetManager.dispose()
    }
}
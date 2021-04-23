package tdt4240.tankathon.game

import com.badlogic.gdx.assets.AssetManager

abstract class Character {
    var maxHealth: Float = 0f
    var speed: Float = 0f
    var damage: Float = 0f
    var fireRate: Float = 0f
    var texture: Int = 0
}

class HeavyCharacter : Character() {
    init {
        maxHealth = 1500f
        speed = 4f
        damage = 50f
        fireRate = 0.3f
        texture = 0
    }
}
class LightCharacter : Character() {
    init {
        maxHealth = 800f
        speed = 10f
        damage = 8f
        fireRate = 0.05f
        texture = 1
    }
}

class GameManager (
        val game: TankathonGame,
        val assetManager: AssetManager,
) {
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

}
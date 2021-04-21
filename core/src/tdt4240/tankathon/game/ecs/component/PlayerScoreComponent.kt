package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PlayerScoreComponent: Component, Pool.Poolable {
    var  playerScore:Float=0.0f
    override fun reset() {
        playerScore=0f
    }


    companion object{
        val mapper:ComponentMapper<PlayerScoreComponent> = mapperFor<PlayerScoreComponent>()
    }


}
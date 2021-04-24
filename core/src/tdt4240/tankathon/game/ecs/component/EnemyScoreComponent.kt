package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class EnemyScoreComponent : Component, Pool.Poolable {
    var scoreGiven: Float=200.0F
    var scorePercentage: Float=1.0F
    var isDead: Boolean = false


    override fun reset() {
        scoreGiven=200.0F
        scorePercentage= 1.0F
        isDead = false
    }

    companion object {
        val mapper: ComponentMapper<EnemyScoreComponent> = mapperFor<EnemyScoreComponent>()
    }


}
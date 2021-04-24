package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import tdt4240.tankathon.game.ecs.component.PlayerScoreComponent
import ktx.ashley.get
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.AIComponent
import tdt4240.tankathon.game.ecs.component.EnemyScoreComponent
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.screens.GameOverScreen
import tdt4240.tankathon.game.screens.SettingsScreen

private val LOG = logger<ScoreSystem>()

class ScoreSystem : IteratingSystem(allOf(AIComponent:: class, EnemyScoreComponent:: class).get()) {
    private val playerEntities by lazy { (engine as ECSengine).getEntitiesFor(
            allOf(PlayerComponent::class).get()) }


    override fun processEntity(entity: Entity, deltaTime: Float) {
        val enemyScoreComponent = entity.get(EnemyScoreComponent.mapper)
        require(enemyScoreComponent != null) {"Entity |entity| must have a enemyScoreComponent. entity =$entity"}
        val enemyScore = entity.getComponent(EnemyScoreComponent::class.java).scoreGiven
        if(enemyScoreComponent.isDead){
            updateScore(enemyScore, playerEntities[0], entity )
        }
    }

    private fun updateScore(enemyScore: Float, player:Entity, enemy: Entity){
        player[PlayerScoreComponent.mapper]?.run{
            playerScore+= enemyScore
        }
        enemy[EnemyScoreComponent.mapper]?.run{
            isScored = true
        }
        val playerScore = player.getComponent(PlayerScoreComponent::class.java).playerScore

        GameOverScreen.setCompanionHigscore(playerScore)
        LOG.info{"Score oppdatert med "+ enemyScore+ "og er nå "+ playerScore}
        println("gamOverScore:"+GameOverScreen.getCompanionHighscore().toString())

    }
}
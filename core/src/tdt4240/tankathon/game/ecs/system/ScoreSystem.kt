package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.info
import ktx.log.logger
import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.AIComponent
import tdt4240.tankathon.game.ecs.component.EnemyScoreComponent
import tdt4240.tankathon.game.ecs.component.PlayerComponent
import tdt4240.tankathon.game.ecs.component.PlayerScoreComponent
import tdt4240.tankathon.game.menuscreens.GameOverScreen

private val LOG = logger<ScoreSystem>()

class ScoreSystem : IteratingSystem(
        allOf(AIComponent:: class, EnemyScoreComponent:: class).get()
) {
    private val playerEntities by lazy { (engine as ECSengine).getEntitiesFor(
            allOf(PlayerComponent::class).get()) }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val enemyScoreComponent = entity[EnemyScoreComponent.mapper]
        require(enemyScoreComponent != null) {"Entity |entity| must have a enemyScoreComponent. entity =$entity"}
        if(enemyScoreComponent.isDead){
            val score = enemyScoreComponent.baseScore * enemyScoreComponent.scorePercentage
            updateScore(score, playerEntities[0])
        }
    }

    private fun updateScore(enemyScore: Float, player:Entity){
        player[PlayerScoreComponent.mapper]?.run{
            playerScore += enemyScore
        }
        val playerScore = player[PlayerScoreComponent.mapper]?.playerScore

        if (playerScore != null) {
            GameOverScreen.setCompanionHigscore(playerScore)
        }
        LOG.info{"Score oppdatert med "+ enemyScore+ "og er nå "+ playerScore}
    }
}
package tdt4240.tankathon.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class ManagementComponent : Component, Pool.Poolable{
    var numberOfWaves: Int = 5
    var remaingNumberOfWaves: Int = numberOfWaves
    var numberOfNPCInWave: Int = 40
    var deltaTimeWaves: Float = 30f //Time between waves
    var countDown: Float = 0f //Begin to countdown from deltaTimeWaves when wave is launched. When <0 new wave
    override fun reset() {
        numberOfWaves = 5
        remaingNumberOfWaves = numberOfWaves
        numberOfNPCInWave = 10
        deltaTimeWaves = 30f
        countDown = 0f
    }


    companion object{
        val mapper: ComponentMapper<ManagementComponent> = mapperFor<ManagementComponent>()
    }
}
package tdt4240.tankathon.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import ktx.ashley.allOf
import ktx.ashley.get

import tdt4240.tankathon.game.ecs.ECSengine
import tdt4240.tankathon.game.ecs.component.*

private var bulletTexture = Texture(Gdx.files.internal("bullet_green.png"))
class FireSystem(private val engine: ECSengine)
 : IteratingSystem(
        allOf(BulletComponent::class, VelocityComponent::class, SpriteComponent::class,
                PositionComponent::class, TransformComponent::class, CanonComponent::class).get()
){
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val bullet = entity[BulletComponent.mapper]
        require(bullet != null){ "Entity |entity| must have a BulletComponent. entity=$entity"}

        val position = entity[PositionComponent.mapper]
        require(position != null){ "Entity |entity| must have a PositionComponent. entity=$entity"}

        val velocity = entity[VelocityComponent.mapper]
        require(velocity != null){ "Entity |entity| must have a VelocityComponent. entity=$entity"}

        val sprite = entity[SpriteComponent.mapper]
        require(sprite != null){ "Entity |entity| must have a SpriteComponent. entity=$entity"}

        val canon = entity[CanonComponent.mapper]
        require(canon != null){ "Entity |entity| must have a CanonComponent. entity=$entity"}

        position.position.add(velocity.getVelocity().scl(deltaTime))


        canon.timer -= deltaTime

    }


    public fun fire(gunnerPosition: Vector3, canon: CanonComponent, gunOrientation: Float){
        if (canon.timer>0){
            return
        }
        engine.addBullet(bulletTexture, gunnerPosition, Vector3(MathUtils.cosDeg(gunOrientation + 90), MathUtils.sinDeg(gunOrientation + 90), 0f))
            canon.amunition-=1
            canon.timer=canon.fireRate.toDouble()
    }

}


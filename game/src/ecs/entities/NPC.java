package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import graphic.Animation;

import java.util.logging.Logger;

/** An abstract superclass to classify certain entities as "Not-Playable-Characters"*/
public abstract class NPC extends Entity{

    private final Logger npcLogger = Logger.getLogger(this.getClass().getSimpleName());
    public void setupAnimationComponent(String pathToIdleLeft, String pathToIdleRight) {
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        new AnimationComponent(this, idleLeft, idleRight);
        npcLogger.info("NPC received AnimationComponent");
    }

    public void setupPositionComponent() {
        new PositionComponent(this);
        npcLogger.info("NPC received PositionComponent");
    }

    public void setupVelocityComponent(float xSpeed, float ySpeed, String animLeft, String animRight) {
        Animation runLeft = AnimationBuilder.buildAnimation(animLeft);
        Animation runRight = AnimationBuilder.buildAnimation(animRight);
        new VelocityComponent(this, xSpeed, ySpeed, runLeft, runRight);
        npcLogger.info("NPC received VelocityComponent");
    }
}

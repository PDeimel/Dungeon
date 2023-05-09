package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.collision.HeroCollisionEnter;
import ecs.components.collision.HeroCollisionOut;
import ecs.components.skill.*;
import graphic.Animation;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero with
 * all its components and attributes .
 */
public class Hero extends Entity{

    private final int fireballCoolDown = 0;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private final String pathToGetHit = "knight/hit";
    private final String pathToDie = "knight/death";
    private final int health = 200;
    private Skill firstSkill;
    private Skill secondSkill;

    /** Entity with Components */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupHealthComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupFireballSkill() {
        firstSkill =
                new Skill(
                        new ArrowSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);

        secondSkill= new Skill(
            new IceBallSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }


    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                new HeroCollisionEnter(),
                new HeroCollisionOut());
    }

    public void setupHealthComponent() {
        Animation getHitAnimation = AnimationBuilder.buildAnimation(pathToGetHit);
        Animation dieAnimation = AnimationBuilder.buildAnimation(pathToDie);
        new HealthComponent(
                this,
                health,
                new HeroOnDeath(),
                getHitAnimation,
                dieAnimation
        );
    }
}

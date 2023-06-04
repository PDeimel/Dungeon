package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.collision.HeroCollisionEnter;
import ecs.components.collision.HeroCollisionOut;
import ecs.components.skill.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import graphic.Animation;
import java.util.logging.Logger;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to set up the hero
 * with all its components and attributes .
 */
public class Hero extends Entity {

    private final int fireballCoolDown = 0;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private final String pathToGetHit = "knight/hit";
    private final String pathToDie = "knight/death";
    private final int health = 100;
    private final int invSlots = 5;
    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private Skill fourthSkill;
    private Skill fifthSkill;
    private final ILevelUp levelUp;
    private final Logger heroLogger;

    /** Entity with Components */
    public Hero() {
        super();
        heroLogger = Logger.getLogger(this.getClass().getName());
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        setupHealthComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupSkill();
        pc.setSkillSlot1(firstSkill);
        pc.setSkillSlot2(secondSkill);
        // Added the Inventory to the hero
        new InventoryComponent(this, invSlots);
        levelUp =
                (long nextLevel) -> {
                    this.getComponent(HealthComponent.class)
                            .ifPresent(
                                    hc -> {
                                        // Grants +5 MaxHP per Level
                                        ((HealthComponent) hc)
                                                .setMaximalHealthpoints(
                                                        ((HealthComponent) hc)
                                                                        .getMaximalHealthpoints()
                                                                + 5);
                                    });
                    this.getComponent(VelocityComponent.class)
                            .ifPresent(
                                    vc -> {
                                        // Grants a small movement buff each level
                                        ((VelocityComponent) vc)
                                                .setCurrentXVelocity(
                                                        ((VelocityComponent) vc)
                                                                        .getCurrentXVelocity()
                                                                + 0.05f);
                                        ((VelocityComponent) vc)
                                                .setCurrentYVelocity(
                                                        ((VelocityComponent) vc)
                                                                        .getCurrentYVelocity()
                                                                + 0.05f);
                                    });
                    heroLogger.info(
                            "You have reached Level "
                                    + nextLevel
                                    + ". Your max health and movement speed have been increased.");
                    switch ((int) nextLevel) {
                        case 5 -> {
                            pc.setSkillSlot3(thirdSkill);
                            heroLogger.info("The ability 'BodyAttack' has been unlocked.");
                        }
                        case 10 -> {
                            pc.setSkillSlot4(fourthSkill);
                            heroLogger.info("The ability 'Steroids' has been unlocked.");
                        }
                        case 20 -> {
                            pc.setSkillSLot5(fifthSkill);
                            heroLogger.info("The ability 'Chronobreak' has been unlocked.");
                        }
                    }
                };
        setupXPComponent();
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

    private void setupSkill() {
        firstSkill =
                new Skill(new ArrowSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);

        secondSkill =
                new Skill(new IceBallSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);

        thirdSkill = new Skill(new BodyAttack(), fireballCoolDown);

        fourthSkill = new Skill(new ChronoBreakSkill(), fireballCoolDown);

        fifthSkill = new Skill(new SteroidsSkill(), fireballCoolDown);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this, new HeroCollisionEnter(), new HeroCollisionOut());
    }

    private void setupHealthComponent() {
        Animation getHitAnimation = AnimationBuilder.buildAnimation(pathToGetHit);
        Animation dieAnimation = AnimationBuilder.buildAnimation(pathToDie);
        new HealthComponent(this, health, new HeroOnDeath(), getHitAnimation, dieAnimation);
    }

    private void setupXPComponent() {
        new XPComponent(this, levelUp);
    }
}

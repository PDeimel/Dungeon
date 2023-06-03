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
    private HealthComponent hc;
    private VelocityComponent vc;
    private Skill firstSkill;
    private Skill secondSkill;
    private Skill thirdSkill;
    private int invSlots = 5;
    private ILevelUp levelUp;

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
        pc.setSkillSlot3(thirdSkill);
        //Added the Inventory to the hero
        new InventoryComponent(this, invSlots);
        setupXPComponent();
        levelUp = (long nextLevel) -> {
          switch((int) nextLevel) {
              case(5) -> {
                  //pc.setSkillSlot4;
                  heroLogger.info("The ability 'Steroids' has been unlocked");
              }
              case(10) -> {
                  //pc.setSkillSlot5;
                  heroLogger.info("The ability 'Chronobreak' has been unlocked");
              }
              default -> {
                  hc.setMaximalHealthpoints(hc.getMaximalHealthpoints() + 5);
                  heroLogger.info("Your max health has been increased by 5");
                  vc.setCurrentXVelocity(vc.getCurrentXVelocity() + 0.005f);
                  vc.setCurrentYVelocity(vc.getCurrentYVelocity() + 0.005f);
                  heroLogger.info("Your movement speed has slightly increased");
              }
          }
        };
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        vc = new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupSkill() {
        firstSkill =
                new Skill(
                        new ArrowSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);

        secondSkill=
                new Skill(
                        new IceBallSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);

        thirdSkill=
                new Skill(
                        new BodyAttack(),fireballCoolDown);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                new HeroCollisionEnter(),
                new HeroCollisionOut());
    }

    private void setupHealthComponent() {
        Animation getHitAnimation = AnimationBuilder.buildAnimation(pathToGetHit);
        Animation dieAnimation = AnimationBuilder.buildAnimation(pathToDie);
        hc = new HealthComponent(
                this,
                health,
                new HeroOnDeath(),
                getHitAnimation,
                dieAnimation
        );
    }

    private void setupXPComponent() {
        new XPComponent(this, levelUp);
    }
}

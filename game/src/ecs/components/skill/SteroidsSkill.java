package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class SteroidsSkill implements ISkillFunction {

    private final int duration = 5;
    private final Logger steroidsSkillLogger = Logger.getLogger(this.getClass().getName());

    /**
     * Velocity of hero turns faster, animation is changed und hero loses 10 health points. The
     * velocity of hero turns 0.1f faster while the Skill is active. After a determined time the
     * animation and velocity of the hero are reset to standard.
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        VelocityComponent vc =
                (VelocityComponent) entity.getComponent(VelocityComponent.class).orElseThrow();

        HealthComponent hc =
                (HealthComponent) entity.getComponent(HealthComponent.class).orElseThrow();
        if (hc.getCurrentHealthpoints() >= 11) {
            hc.setCurrentHealthpoints(hc.getCurrentHealthpoints() - 10);
            steroidsSkillLogger.info("Hero lost 10 health points because SteroidsSkill was used");
            vc.setYVelocity(vc.getYVelocity() + 0.1f);
            vc.setXVelocity(vc.getXVelocity() + 0.1f);
            steroidsSkillLogger.info("the hero becomes faster");

            Animation newAnimationR =
                    AnimationBuilder.buildAnimation("character/monster/chort/runRight");
            Animation newAnimationL =
                    AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
            vc.setMoveLeftAnimation(newAnimationL);
            vc.setMoveRightAnimation(newAnimationR);
            steroidsSkillLogger.info(" new animation of Hero was added to hero");

            AnimationComponent ac =
                    (AnimationComponent)
                            entity.getComponent(AnimationComponent.class).orElseThrow();
            new AnimationComponent(entity, newAnimationL, newAnimationR);
            // Deactivates the ability after a set amount of time via a Timer.
            Timer time = new Timer();
            time.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            vc.setXVelocity(0.3f);
                            vc.setYVelocity(0.3f);
                            steroidsSkillLogger.info("the velocity of hero turned to standard");
                            vc.setMoveLeftAnimation(
                                    AnimationBuilder.buildAnimation("character/knight/runLeft"));
                            vc.setMoveRightAnimation(
                                    AnimationBuilder.buildAnimation("character/knight/runRight"));
                            steroidsSkillLogger.info("the hero received the old animation to run");
                            new AnimationComponent(
                                    entity,
                                    AnimationBuilder.buildAnimation(
                                            "game/assets/character/knight/idleLeft"),
                                    AnimationBuilder.buildAnimation("character/knight/idleRight"));
                            steroidsSkillLogger.info(
                                    "the hero received the old animation to left und right side");
                        }
                    },
                    duration * 1000);
        } else {
            steroidsSkillLogger.info(
                    "Hero can not use SteroidsSkill. Health points are lower than 20");
        }
    }
}

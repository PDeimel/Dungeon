package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.VelocityComponent;
import ecs.entities.*;
import graphic.Animation;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import starter.Game;

public class ChronoBreakSkill implements ISkillFunction {

    private final int duration = 5;
    private Logger chronoBreakLogger = Logger.getLogger(this.getClass().getName());

    /**
     * Stop the Mosters and change the animation
     *
     * The Monsters lost the velocity in y and x . They become a new Animation when they are stopped.
     * After a determinate time the Velocity and Animation are set to standard
     *
     * @param entity which uses the skill
     */

    @Override
    public void execute(Entity entity) {
        Set<Entity> myEntities = Game.getEntities();
        for (Entity a : myEntities) {
            if (a instanceof BatMonster) {
                modifyTheBat(a);
            } else if (a instanceof PigMonster) {
                modifyThePig(a);
            } else if (a instanceof WormMonster) {
                modifyTheWorm(a);
            }
        }
    }

    /**
     * set the velocity and animation of bat. Calculate the time to set the animation
     * and velocity to standard
     *
     * @param bat used to change the characteristics of Bat
     */

    private void modifyTheBat(Entity bat) {
        BatMonster b = (BatMonster) bat;
        VelocityComponent vc =
                (VelocityComponent) b.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        chronoBreakLogger.info("Velocity of Bat is now null because ChronoBreak was used");

        AnimationComponent ac =
                (AnimationComponent) b.getComponent(AnimationComponent.class).orElseThrow();
        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");
        chronoBreakLogger.info("Animation of Bat is now a snowflake because ChronoBreak was used");

        new AnimationComponent(b, newAnimation);
        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        chronoBreakLogger.info("Velocity of Bat turned to the normal velocity");

                        new AnimationComponent(
                                bat,
                                AnimationBuilder.buildAnimation(
                                        "character/monster/bat/idleAndRunLeft"),
                                AnimationBuilder.buildAnimation(
                                        "character/monster/bat/idleAndRunRight"));
                        chronoBreakLogger.info("Animation of Bat turned to the normal animation");
                    }
                },
                duration * 1000);
    }

    /**
     * set the velocity and animation of Pig. Calculate the time to set the animation
     * and velocity to standard
     *
     * @param pig used to change the characteristics of pig
     */

    private void modifyThePig(Entity pig) {
        PigMonster b = (PigMonster) pig;
        VelocityComponent vc =
                (VelocityComponent) b.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        chronoBreakLogger.info("Velocity of Pig is now null because ChronoBreak was used");

        AnimationComponent ac =
                (AnimationComponent) b.getComponent(AnimationComponent.class).orElseThrow();
        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");
        new AnimationComponent(b, newAnimation);
        chronoBreakLogger.info("Animation of Pig is now a snowflake because ChronoBreak was used");

        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        chronoBreakLogger.info("Velocity of Pig turned to the normal velocity");

                        new AnimationComponent(
                                pig,
                                AnimationBuilder.buildAnimation("character/monster/pig/idleLeft"),
                                AnimationBuilder.buildAnimation("character/monster/pig/idleRight"));
                        chronoBreakLogger.info("Animation of Pig turned to the normal animation");
                    }
                },
                duration * 1000);
    }


    /**
     * set the velocity and animation of Worm. Calculate the time to set the animation
     * and velocity to standard
     * @param worm used to change the characteristics of Worm
     */
    private void modifyTheWorm(Entity worm) {
        WormMonster b = (WormMonster) worm;
        VelocityComponent vc =
                (VelocityComponent) b.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        chronoBreakLogger.info("Velocity of Worm is now null because ChronoBreak was used");

        AnimationComponent ac =
                (AnimationComponent) b.getComponent(AnimationComponent.class).orElseThrow();

        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");
        new AnimationComponent(b, newAnimation);
        chronoBreakLogger.info("Animation of Worm is now a snowflake because ChronoBreak was used");

        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        chronoBreakLogger.info("Velocity of Worm turned to the normal velocity");

                        new AnimationComponent(
                                worm,
                                AnimationBuilder.buildAnimation("character/monster/worm/idleLeft"),
                                AnimationBuilder.buildAnimation(
                                        "character/monster/worm/idleRight"));
                        chronoBreakLogger.info("Animation of Worm turned to the normal Animation");
                    }
                },
                duration * 1000);
    }
}

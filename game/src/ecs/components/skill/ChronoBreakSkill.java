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
    private final Logger chronoBreakLogger = Logger.getLogger(this.getClass().getName());

    /**
     * Stops the Monsters and changes their animation. The Monsters lose the velocity in y and x.
     * They receive a new Animation when they are stopped. After a determined time the Velocity and
     * Animation are reset to standard
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        Set<Entity> myEntities = Game.getEntities();
        for (Entity a : myEntities) {
            if (a instanceof Monster) modifyMonster((Monster) a);
        }
    }

    /**
     * Is activated through the iteration in the execute-method and changes the monsters specific
     * Velocity and Animation into a frozen Flake state which is reset by a timer after a specific
     * period of time.
     *
     * @param monster The current monster of the loop
     */
    private void modifyMonster(Monster monster) {
        String monsterName = monster.getClass().getSimpleName();
        VelocityComponent vc =
                (VelocityComponent) monster.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        chronoBreakLogger.info(
                "Velocity of " + monsterName + " is now null because ChronoBreak was used");
        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");
        new AnimationComponent(monster, newAnimation);
        chronoBreakLogger.info(
                "Animation of " + monsterName + " is now a snowflake because ChronoBreak was used");
        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        chronoBreakLogger.info(
                                "Velocity of " + monsterName + " returned to the normal velocity");
                        if (monster.getClass() == WormMonster.class) {
                            new AnimationComponent(
                                    monster,
                                    AnimationBuilder.buildAnimation(
                                            "character/monster/worm/idleLeft"),
                                    AnimationBuilder.buildAnimation(
                                            "character/monster/worm/idleRight"));
                            vc.setXVelocity(0.05f);
                            vc.setYVelocity(0.1f);
                        } else if (monster.getClass() == BatMonster.class) {
                            new AnimationComponent(
                                    monster,
                                    AnimationBuilder.buildAnimation(
                                            "character/monster/bat/idleAndRunLeft"),
                                    AnimationBuilder.buildAnimation(
                                            "character/monster/bat/idleAndRunRight"));
                            vc.setXVelocity(0.2f);
                            vc.setYVelocity(0.2f);
                        } else if (monster.getClass() == PigMonster.class) {
                            new AnimationComponent(
                                    monster,
                                    AnimationBuilder.buildAnimation(
                                            "character/monster/pig/idleLeft"),
                                    AnimationBuilder.buildAnimation(
                                            "character/monster/pig/idleRight"));
                            vc.setXVelocity(0.15f);
                        }
                        chronoBreakLogger.info(
                                "Animation of "
                                        + monsterName
                                        + " returned to the normal Animation");
                    }
                },
                duration * 1000);
    }
}

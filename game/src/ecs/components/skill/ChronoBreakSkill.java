package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.VelocityComponent;
import ecs.entities.*;
import graphic.Animation;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import starter.Game;

public class ChronoBreakSkill implements ISkillFunction {

    private final int duration = 5;

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

    private void modifyTheBat(Entity bat) {
        BatMonster b = (BatMonster) bat;
        VelocityComponent vc =
                (VelocityComponent) b.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        AnimationComponent ac =
                (AnimationComponent) b.getComponent(AnimationComponent.class).orElseThrow();
        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");

        new AnimationComponent(b, newAnimation);
        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        new AnimationComponent(
                                bat,
                                AnimationBuilder.buildAnimation(
                                        "character/monster/bat/idleAndRunLeft"),
                                AnimationBuilder.buildAnimation(
                                        "character/monster/bat/idleAndRunRight"));
                    }
                },
                duration * 1000);
    }

    private void modifyThePig(Entity pig) {
        PigMonster b = (PigMonster) pig;
        VelocityComponent vc =
                (VelocityComponent) b.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        AnimationComponent ac =
                (AnimationComponent) b.getComponent(AnimationComponent.class).orElseThrow();
        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");

        new AnimationComponent(b, newAnimation);

        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        new AnimationComponent(
                                pig,
                                AnimationBuilder.buildAnimation("character/monster/pig/idleLeft"),
                                AnimationBuilder.buildAnimation("character/monster/pig/idleRight"));
                    }
                },
                duration * 1000);
    }

    private void modifyTheWorm(Entity worm) {
        WormMonster b = (WormMonster) worm;
        VelocityComponent vc =
                (VelocityComponent) b.getComponent(VelocityComponent.class).orElseThrow();
        vc.setXVelocity(0f);
        vc.setYVelocity(0f);
        AnimationComponent ac =
                (AnimationComponent) b.getComponent(AnimationComponent.class).orElseThrow();

        Animation newAnimation =
                AnimationBuilder.buildAnimation("skills/fireball/IceBall/snowflacke.png");
        new AnimationComponent(b, newAnimation);
        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        new AnimationComponent(
                                worm,
                                AnimationBuilder.buildAnimation("character/monster/worm/idleLeft"),
                                AnimationBuilder.buildAnimation(
                                        "character/monster/worm/idleRight"));
                    }
                },
                duration * 1000);
    }
}

package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.Timer;
import java.util.TimerTask;

public class SteroidsSkill implements ISkillFunction {

    private final int duration = 5;

    @Override
    public void execute(Entity entity) {
        VelocityComponent vc =
                (VelocityComponent) entity.getComponent(VelocityComponent.class).orElseThrow();

        HealthComponent hc =
                (HealthComponent) entity.getComponent(HealthComponent.class).orElseThrow();
        hc.setCurrentHealthpoints(hc.getCurrentHealthpoints() - 10);

        vc.setYVelocity(vc.getYVelocity() + 0.1f);
        vc.setXVelocity(vc.getXVelocity() + 0.1f);

        Animation newAnimationR =
                AnimationBuilder.buildAnimation("character/monster/chort/runRight");
        Animation newAnimationL =
                AnimationBuilder.buildAnimation("character/monster/chort/runLeft");
        vc.setMoveLeftAnimation(newAnimationL);
        vc.setMoveRightAnimation(newAnimationR);

        AnimationComponent ac =
                (AnimationComponent) entity.getComponent(AnimationComponent.class).orElseThrow();
        new AnimationComponent(entity, newAnimationL, newAnimationR);

        Timer time = new Timer();
        time.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        vc.setXVelocity(0.3f);
                        vc.setYVelocity(0.3f);
                        vc.setMoveLeftAnimation(
                                AnimationBuilder.buildAnimation("character/knight/runLeft"));
                        vc.setMoveRightAnimation(
                                AnimationBuilder.buildAnimation("character/knight/runRight"));
                        new AnimationComponent(
                                entity,
                                AnimationBuilder.buildAnimation(
                                        "game/assets/character/knight/idleLeft"),
                                AnimationBuilder.buildAnimation("character/knight/idleRight"));
                    }
                },
                duration * 1000);
    }
}

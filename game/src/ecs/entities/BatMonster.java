package ecs.entities;

import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;
import java.util.List;

/** A monster resembling a bat which is exceptionally fast and tries to hunt down the hero */
public class BatMonster extends Monster {

    private final float XSPEED = 0.2f;
    private final float YSPEED = 0.2f;
    private IFightAI iFightAI = new CollideAI(0.2f);
    private IIdleAI idleAI = new PatrouilleWalk(5f, 10, 20, PatrouilleWalk.MODE.RANDOM);
    private ITransition transition = new RangeTransition(4f);

    public BatMonster() {
        super();
        super.setxSpeed(XSPEED);
        super.setySpeed(YSPEED);
        super.setPathToIdleLeft("character/monster/bat/idleAndRunLeft");
        super.setPathToIdleRight("character/monster/bat/idleAndRunRight");
        super.setPathToRunLeft("character/monster/bat/idleAndRunLeft");
        super.setPathToRunRight("character/monster/bat/idleAndRunRight");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(7);
        new PositionComponent(this);
        new AnimationComponent(this, super.getIdleLeft(), super.getIdleRight());
        new VelocityComponent(
                this,
                super.getxSpeed(),
                super.getySpeed(),
                super.getIdleLeft(),
                super.getIdleRight());
        new HitboxComponent(this, super.getMonsterCollisionEnter(), super.getMonsterCollisionOut());
        new AIComponent(this, iFightAI, idleAI, transition);
        Animation missingTextureAnimation =
                new Animation(List.of("animation/missingTexture.png"), 100);

        /**
         * Added a new HealthComponent to Monster, with this Component the Monter can die if the
         * Life of Monster is lower than null or equal, the HealthSystem will remove the Entity
         */
        new HealthComponent(
                this,
                100,
                (Entity e) -> {
                    HealthComponent hc =
                            (HealthComponent) e.getComponent(HealthComponent.class).orElseThrow();
                },
                missingTextureAnimation,
                missingTextureAnimation);
    }
}

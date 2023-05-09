package ecs.entities;

import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;

import ecs.components.ai.idle.StaticRadiusLongWay;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import graphic.Animation;

import java.util.List;

/** A monster resembling a pig only being able to move horizontally but to compensate
 * is very tanky
 */
public class PigMonster extends Monster {

    private final float XSPEED = 0.15f;
    private final float YSPEED = 0f;
    private IFightAI iFightAI = new CollideAI(0.4f);
    IIdleAI idleAI = new StaticRadiusLongWay(10f,5);
    private ITransition transition = new RangeTransition(3f);

    public PigMonster(){
        super();
        super.setxSpeed(XSPEED);
        super.setySpeed(YSPEED);
        super.setPathToIdleLeft("character/monster/pig/idleLeft");
        super.setPathToIdleRight("character/monster/pig/idleRight");
        super.setPathToRunLeft("character/monster/pig/runLeft");
        super.setPathToRunRight("character/monster/pig/runRight");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(5);
        new PositionComponent(this);
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());
        new VelocityComponent(this,super.getxSpeed(),super.getySpeed(),super.getIdleLeft(),super.getIdleRight());
        new HitboxComponent(this,super.getMonsterCollisionEnter(),super.getMonsterCollisionOut());
        new AIComponent(this,iFightAI,idleAI,transition);
        Animation missingTextureAnimation = new Animation(List.of("animation/missingTexture.png"), 100);

        /**
         *Added a new HealthComponent to Monster, with this Component the Monter can die
         * if the Life of Monster is lower than null or equal, the HealthSystem will remove the Entity
         */
        new HealthComponent(this, 100, (Entity e) -> {
            HealthComponent hc= (HealthComponent) e.getComponent(HealthComponent.class).orElseThrow();
        }, missingTextureAnimation, missingTextureAnimation);
    }
}

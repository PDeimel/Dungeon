package ecs.entities;

import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;

/**
 * A harmless ghost that follows the hero around most of the time but is sometimes
 * able to move around freely or disappear completely from the level. Once he is in close
 * reach of his gravestone, he will leave the level behind.
 */
public class Ghost extends Monster{
    private final float XSPEED = 0.2f;
    private final float YSPEED = 0.2f;
    private IFightAI iFightAI = new CollideAI(10f);
    private IIdleAI idleAI = new RadiusWalk(15, 10);
    private ITransition transition = new RangeTransition(2f);
    public Ghost() {
        super();
        super.setxSpeed(XSPEED);
        super.setySpeed(YSPEED);
        super.setPathToIdleLeft("character/monster/ghost/idleAndRunLeft");
        super.setPathToIdleRight("character/monster/ghost/idleAndRunRight");
        super.setPathToRunLeft("character/monster/ghost/idleAndRunLeft");
        super.setPathToRunRight("character/monster/ghost/idleAndRunRight");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(0);
        new PositionComponent(this);
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());
        new VelocityComponent(this,super.getxSpeed(),super.getySpeed(),super.getIdleLeft(),super.getIdleRight());
        new HitboxComponent(this,super.getMonsterCollisionEnter(),super.getMonsterCollisionOut());
        new AIComponent(this,iFightAI,idleAI,transition);
    }
}

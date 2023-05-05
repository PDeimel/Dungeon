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

/** A monster resembling a worm which is slow and weak, mainly used in the early levels of the dungeon*/
public class WormMonster extends Monster{

    private final float XSPEED = 0.05f;
    private final float YSPEED = 0.1f;
    private IFightAI iFightAI = new CollideAI(0.05f);
    private IIdleAI idleAI = new RadiusWalk(15, 10);
    private ITransition transition = new RangeTransition(2f);
    public WormMonster() {
        super();
        super.setxSpeed(XSPEED);
        super.setySpeed(YSPEED);
        super.setPathToIdleLeft("character/monster/worm/idleLeft");
        super.setPathToIdleRight("character/monster/worm/idleRight");
        super.setPathToRunLeft("character/monster/worm/runLeft");
        super.setPathToRunRight("character/monster/worm/runRight");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(1);
        new PositionComponent(this);
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());
        new VelocityComponent(this,super.getxSpeed(),super.getySpeed(),super.getIdleLeft(),super.getIdleRight());
        new HitboxComponent(this,super.getMonsterCollisionEnter(),super.getMonsterCollisionOut());
        new AIComponent(this,iFightAI,idleAI,transition);
    }
}

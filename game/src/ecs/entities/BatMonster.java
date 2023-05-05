package ecs.entities;

import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;

/** A monster resembling a bat which is exceptionally fast and tries to hunt down the hero */
public class BatMonster extends Monster{

    private final float XSPEED = 0.2f;
    private final float YSPEED = 0.3f;
    private IFightAI iFightAI = new CollideAI(0.2f);
    private IIdleAI idleAI = new PatrouilleWalk(5f,10,20, PatrouilleWalk.MODE.RANDOM);
    private ITransition transition = new RangeTransition(4f);

    public BatMonster(){
        super();
        super.setxSpeed(XSPEED);
        super.setySpeed(YSPEED);
        super.setPathToIdleLeft("character/monster/bat/idleAndRunLeft");
        super.setPathToIdleRight("character/monster/bat/idleAndRunRight");
        super.setPathToRunLeft("character/monster/bat/idleAndRunLeft");
        super.setPathToRunRight("character/monster/bat/idleAndRunRight");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(10);
        new PositionComponent(this);
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());
        new VelocityComponent(this,super.getxSpeed(),super.getySpeed(),super.getIdleLeft(),super.getIdleRight());
        new HitboxComponent(this,super.getMonsterCollisionEnter(),super.getMonsterCollisionOut());
        new AIComponent(this,iFightAI,idleAI,transition);
    }
}

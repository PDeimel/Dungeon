package ecs.entities;

import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;

/**
 * A gravestone belonging to the ghost which spawns in the same level. Once the hero as well as the ghost
 * are in proximity to the stone, the ghost will disappear and the hero will either be rewarded or punished.
 */
public class Gravestone extends Monster{

    public Gravestone() {
        super();
        super.setPathToIdleLeft("character/monster/gravestone");
        super.setPathToIdleRight("character/monster/gravestone");
        super.setPathToRunLeft("character/monster/gravestone");
        super.setPathToRunRight("character/monster/gravestone");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(0);
        new PositionComponent(this);
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());
        new HitboxComponent(this,super.getMonsterCollisionEnter(),super.getMonsterCollisionOut());
    }
}
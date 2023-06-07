package ecs.entities;

import ecs.components.*;

/** An entity that poses a riddle to the player */
public class Riddleman extends Monster {
    public Riddleman(){
        super();
        super.setPathToIdleLeft("character/monster/rätzelmeister/idle");
        super.setPathToIdleRight("character/monster/rätzelmeister/idle");
        super.setPathToRunLeft("character/monster/rätzelmeister/idle");
        super.setPathToRunRight("character/monster/rätzelmeister/idle");
        super.setIdleLeft();
        super.setIdleRight();
        new PositionComponent(this);
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());

    }
}

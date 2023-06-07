package ecs.entities;

import ecs.components.*;

/** An entity that poses a riddle to the player */
public class Riddleman extends NPC {

    private final String pathToIdle = "character/monster/riddleman";
    public Riddleman(){
        super();
        super.setupPositionComponent();
        super.setupAnimationComponent(pathToIdle, pathToIdle);
    }
}

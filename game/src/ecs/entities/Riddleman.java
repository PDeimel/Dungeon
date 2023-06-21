package ecs.entities;

import ecs.components.*;

/** An entity that poses a riddle to the player via an implementation if IInteraction. */
public class Riddleman extends NPC {

    private final String pathToIdle = "character/monster/riddleman";

    public Riddleman() {
        super();
        super.setupPositionComponent();
        super.setupAnimationComponent(pathToIdle, pathToIdle);
        new InteractionComponent(this, 1f, false, new RiddleInteraction());
    }
}

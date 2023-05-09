package ecs.components.ai.transition;

import ecs.entities.Entity;

/**
 * A transition made for moving NPC-entities which are unable to strike the hero.
 */
public class PassiveTransition implements ITransition {
    @Override
    public boolean isInFightMode(Entity entity) {
        return false;
    }
}

package ecs.entities;

import ecs.components.HitboxComponent;

/**
 * A gravestone belonging to the ghost which spawns in the same level. Once the hero as well as the
 * ghost are in proximity to the stone, the ghost will disappear and the hero will either be
 * rewarded or punished.
 */
public class Gravestone extends NPC {

    private final String pathToIdleLeft = "character/monster/gravestone";
    private final String pathToIdleRight = "character/monster/gravestone";
    private boolean activated = false;
    private final Ghost ghost;

    public Gravestone(Ghost ghost) {
        super();
        super.setupAnimationComponent(pathToIdleLeft, pathToIdleRight);
        super.setupPositionComponent();
        setupHitboxComponent();
        this.ghost = ghost;
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (you, other, direction) -> giveReward(other),
                (you, other, direction) -> System.out.println());
    }

    /**
     * When the hero, accompanied by a ghost, gets in close range to the gravestone, the ghost
     * activates his reward-method ONCE.
     *
     * @param hero the current playable hero
     */
    public void giveReward(Entity hero) {
        if (hero instanceof Hero && !activated) {
            activated = true;
            ghost.graveLoot();
        }
    }
}

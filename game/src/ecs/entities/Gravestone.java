package ecs.entities;

import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;

/**
 * A gravestone belonging to the ghost which spawns in the same level. Once the hero as well as the ghost
 * are in proximity to the stone, the ghost will disappear and the hero will either be rewarded or punished.
 */
public class Gravestone extends Monster{

    private boolean activated = false;
    private final Ghost ghost;

    public Gravestone(Ghost ghost) {
        super();
        super.setPathToIdleLeft("character/monster/gravestone");
        super.setPathToIdleRight("character/monster/gravestone");
        super.setPathToRunLeft("character/monster/gravestone");
        super.setPathToRunRight("character/monster/gravestone");
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(0);
        setUpAnimationComponent();
        setUpPositionComponent();
        setUpHitboxComponent();
        this.ghost = ghost;
    }

    public void setUpAnimationComponent() {
        new AnimationComponent(this,super.getIdleLeft(),super.getIdleRight());
    }

    public void setUpPositionComponent() {
        new PositionComponent(this);
    }

    public void setUpHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> giveReward(other),
            (you, other, direction) -> System.out.println()
        );
    }

    /**
     * When the hero, accompanied by a ghost, gets in close range to the gravestone, the
     * ghost activates his reward-method ONCE.
     * @param hero the current playable hero
     */
    public void giveReward(Entity hero) {
        if(hero instanceof Hero && !activated) {
            activated = true;
            ghost.graveLoot();
        }
    }
}

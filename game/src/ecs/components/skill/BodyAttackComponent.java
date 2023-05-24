package ecs.components.skill;

import ecs.components.Component;
import ecs.damage.Damage;
import ecs.entities.Entity;
import tools.Point;

public class BodyAttackComponent extends Component {
    /**
     * Create a new component and add it to the associated entity
     *
     * The component is used to recognize the attack as body attack when collision happens
     *
     * @param entity associated entity
     */
    public BodyAttackComponent(Entity entity) {
        super(entity);

    }
}

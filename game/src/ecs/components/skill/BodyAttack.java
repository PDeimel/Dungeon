package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class BodyAttack extends BodyAttackSkill {

    /** Represent the Body attack in the Game with the characteristics */
    public BodyAttack() {
        super(new Damage(8, DamageType.PHYSICAL, null), new Point(1, 1), "character/knight/rot");
    }
}

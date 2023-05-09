package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class ArrowSkill extends DamageProjectileSkill {

    public ArrowSkill( ITargetSelection selectionFunction) {
        super(
            "game/assets/skills/fireball/Arrow",
            0.5f,
            new Damage(1, DamageType.FIRE, null),
            new Point(10, 10),
            selectionFunction,
            5f);
    }
}

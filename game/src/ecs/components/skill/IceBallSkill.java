package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class IceBallSkill extends CurvedDamageProjectileSkill{

    public IceBallSkill(ITargetSelection targetSelection) {
        super(
            "skills/fireball/fireBall_Down/",
            0.5f,
            new Damage(1, DamageType.FIRE, null),
            new Point(10, 10),
            targetSelection,
            5f);
    }
}

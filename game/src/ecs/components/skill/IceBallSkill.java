package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class IceBallSkill extends CurvedDamageProjectileSkill{

    /**
     * Class represents the Iceball in the game
     * @param targetSelection
     */

    public IceBallSkill(ITargetSelection targetSelection) {
        super(
            "skills/fireball/IceBall/snowflacke.png",
            0.5f,
            new Damage(1, DamageType.ICE, null),
            new Point(10, 10),
            targetSelection,
            5f);
    }
}

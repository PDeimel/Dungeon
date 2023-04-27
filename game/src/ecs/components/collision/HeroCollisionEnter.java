package ecs.components.collision;

import ecs.components.HealthComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monster;
import level.elements.tile.Tile;

public class HeroCollisionEnter implements ICollide {

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        if (a instanceof Hero && b instanceof Monster) {
            HealthComponent heroHealth =(HealthComponent) a.getComponent(HealthComponent.class).orElse(null);
            if (heroHealth != null) {
                int monsterDamageAmount = ((Monster) b).getDmg();
                DamageType monsterDamageType = DamageType.PHYSICAL;

                // create a DamageObject and apply the damage to hero
                Damage damage = new Damage(monsterDamageAmount, monsterDamageType, b);
                heroHealth.receiveHit(damage);
            }
        }
    }
}

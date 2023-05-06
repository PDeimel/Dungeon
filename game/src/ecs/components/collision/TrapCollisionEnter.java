package ecs.components.collision;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Trap;
import ecs.entities.Hero;
import graphic.Animation;
import level.elements.tile.Tile;

/**
 * Activates when the Hero is in range of the trap.
 */
public class TrapCollisionEnter implements ICollide {

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        Entity hero, trap;
        if (a instanceof Hero && b instanceof Trap) {
            hero = a;
            trap = b;
        } else if (a instanceof Trap && b instanceof Hero) {
            hero = b;
            trap = a;
        } else {
            return;
        }
        HealthComponent heroHealth = (HealthComponent) hero.getComponent(HealthComponent.class).orElse(null);
        if (heroHealth != null) {
            int trapDamageAmount = 10;
            DamageType damageType = DamageType.PHYSICAL;
            Damage damage = new Damage(trapDamageAmount, damageType, trap);
            heroHealth.receiveHit(damage);
        }
        AnimationComponent trapAnimation = (AnimationComponent) trap.getComponent(AnimationComponent.class).orElse(null);
        if (trapAnimation != null) {
            Animation activated = AnimationBuilder.buildAnimation("character/trap.spikeTrap/spikeTrap_active.png");
            trapAnimation.setCurrentAnimation(activated);
        }
    }
}

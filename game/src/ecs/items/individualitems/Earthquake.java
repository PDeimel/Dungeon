package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.items.*;
import starter.Game;

import java.util.Set;

/**
 * An individual item that deals damage to everything on the floor which has health
 */
public class Earthquake extends ItemData implements IOnUse {
    private final int DMG = 10;

    public Earthquake() {
        super(
            ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/earthquake/earthquake_inventory.png"),
            AnimationBuilder.buildAnimation("objects/items/earthquake/earthquake_world.png"),
            "Earthquake",
            "An Earthquake which damages all units in the current level."
        );
        this.setOnUse(this);
    }

    /**
     * Gets a list of all entities in the level, checks if it has a HealthComponent,
     * and if so, deals 10 physical damage to them.
     *
     * @param e The entity that used the item.
     * @param item The item that was used.
     */
    @Override
    public void onUse(Entity e, ItemData item) {
        Set<Entity> allEntities = Game.getEntities();
        for (Entity entity : allEntities) {
            entity.getComponent(HealthComponent.class)
                .ifPresent(hc -> {
                    Damage dmg = new Damage(
                        DMG,
                        DamageType.PHYSICAL,
                        null
                    );
                    ((HealthComponent)hc).receiveHit(dmg);
                });
        }
        System.out.println("All Units on the floor were damaged by " + DMG + " dmg.");

        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                ((InventoryComponent) ic).removeItem(this);
            });
    }
}

package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.items.*;
import starter.Game;

import java.util.Set;

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
        WorldItemBuilder.buildWorldItem(this);
        this.setOnUse(this);
    }

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
    }
}

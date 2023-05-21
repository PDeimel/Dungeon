package ecs.items;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import starter.Game;

import java.util.Set;

public class Earthquake extends ItemData implements IOnCollect{
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
        this.setOnCollect(this);
    }

    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        Game.removeEntity(WorldItemEntity);
        Set<Entity> allEntities = Game.getEntities();
        for (Entity entity : allEntities) {
            entity.getComponent(HealthComponent.class)
                .ifPresent(hc -> {
                    Damage dmg = new Damage(
                        DMG,
                        DamageType.PHYSICAL,
                        WorldItemBuilder.buildWorldItem(this)
                    );
                    ((HealthComponent)hc).receiveHit(dmg);
                });
        }
        System.out.println("All Units on the floor were damaged by " + DMG + " dmg.");
    }
}

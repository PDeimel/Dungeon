package ecs.items;

import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import starter.Game;

import java.util.Set;

public class EarthquakeEffect implements IOnUse{
    private final int DMG = 10;
    @Override
    public void onUse(Entity e, ItemData item) {
        Set<Entity> allEntities = Game.getEntities();
        for (Entity entity : allEntities) {
            entity.getComponent(HealthComponent.class)
                .ifPresent(hc -> {
                    Damage dmg = new Damage(
                        DMG,
                        DamageType.PHYSICAL,
                        WorldItemBuilder.buildWorldItem(item)
                    );
                    ((HealthComponent)hc).receiveHit(dmg);
                });
        }
        System.out.println("All Units on the floor were damaged by " + DMG + " dmg.");
        e.getComponent(InventoryComponent.class)
            .ifPresent(ic -> {
                ((InventoryComponent) ic).removeItem(item);
            });
    }
}

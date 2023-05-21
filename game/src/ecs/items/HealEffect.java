package ecs.items;

import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.entities.Entity;

public class HealEffect implements IOnUse{
    @Override
    public void onUse(Entity e, ItemData item) {
        e.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                int currHp = ((HealthComponent) hc).getCurrentHealthpoints();
                int maxHp = ((HealthComponent)hc).getMaximalHealthpoints();
                if(currHp < maxHp -20) {
                    int newHp = currHp + 20;
                    ((HealthComponent)hc).setCurrentHealthpoints(newHp);

                    e.getComponent(InventoryComponent.class)
                        .ifPresent(ic -> {
                            ((InventoryComponent) ic).removeItem(item);
                        });
                    System.out.println("Cake regenerated 20 HP");
                }
                else {
                    ((HealthComponent)hc).setCurrentHealthpoints(maxHp);

                    e.getComponent(InventoryComponent.class)
                        .ifPresent(ic -> {
                            ((InventoryComponent) ic).removeItem(item);
                        });
                    System.out.println("Cake healed full");
                }
            });
    }
}

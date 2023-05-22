package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.items.*;
import starter.Game;

public class Cake extends ItemData implements IOnUse {

    public Cake() {
        super(
            ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/cake/cake_inventory.png"),
            AnimationBuilder.buildAnimation("objects/items/cake/cake_world.png"),
            "Cake",
            "A delicious cake that restores your health to up to 20 HP."
        );
        WorldItemBuilder.buildWorldItem(this);
        this.setOnUse(this);
    }

    @Override
    public void onUse(Entity e, ItemData item) {
        e.getComponent(HealthComponent.class)
            .ifPresent(hc -> {
                int currHp = ((HealthComponent) hc).getCurrentHealthpoints();
                int maxHp = ((HealthComponent) hc).getMaximalHealthpoints();
                if (currHp < maxHp - 20) {
                    int newHp = currHp + 20;
                    ((HealthComponent) hc).setCurrentHealthpoints(newHp);
                    System.out.println("Cake regenerated 20 HP");
                }
                else {
                    ((HealthComponent) hc).setCurrentHealthpoints(maxHp);
                    System.out.println("Cake healed full");
                }
            });
    }
}

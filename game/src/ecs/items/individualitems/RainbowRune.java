package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.items.*;
import starter.Game;
import starter.SpawnMonsters;

import java.util.Random;
import java.util.Set;

public class RainbowRune extends ItemData implements IOnUse {
    Random rd = new Random();
    public RainbowRune() {
        super(
            ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/rainbowrune/rainbowrune_inventory.png"),
            AnimationBuilder.buildAnimation("objects/items/rainbowrune/rainbowrune_world.png"),
            "Rainbow-Rune",
            "A shiny colorful rune that grants a random effect. Always gamble children!"
        );
        WorldItemBuilder.buildWorldItem(this);
        this.setOnUse(this);
    }

    @Override
    public void onUse(Entity e, ItemData item) {
        int res = rd.nextInt(3);
        if(res == 0) {
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
            System.out.println("The Rainbow-Rune healed you.");
        }
        else if(res == 1) {
            Set<Entity> allEntities = Game.getEntities();
            for (Entity entity : allEntities) {
                entity.getComponent(HealthComponent.class)
                    .ifPresent(hc -> {
                        Damage dmg = new Damage(
                            10,
                            DamageType.PHYSICAL,
                            null
                        );
                        ((HealthComponent)hc).receiveHit(dmg);
                    });
            }
            System.out.println("The Rainbow-Rune created an earthquake.");
        }
        else {
            new SpawnMonsters(0).onLevelLoad();
            System.out.println("The Rainbow-Rune spawned many monsters.");
        }
    }
}

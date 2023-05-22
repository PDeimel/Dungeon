package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.entities.Entity;
import ecs.items.IOnCollect;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;

public class Bag extends ItemData{

    public Bag() {
        super(
            ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/bag/bag_inventory.png"),
            AnimationBuilder.buildAnimation("objects/items/bag/bag_world.png"),
            "Bag",
            "A shabby old bag that can carry a few items."
        );
        WorldItemBuilder.buildWorldItem(this);
    }
}

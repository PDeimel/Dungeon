package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.InventoryComponent;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;

/**
 * An individual item which is supposed to expand the hero's inventory when collected
 */
public class Bag extends ItemData{

    public Bag() {
        super(
            ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/bag/bag_inventory.png"),
            AnimationBuilder.buildAnimation("objects/items/bag/bag_world.png"),
            "Bag",
            "A shabby old bag that can carry a few items."
        );
    }
    //TODO: Make the bags inventory functional
}

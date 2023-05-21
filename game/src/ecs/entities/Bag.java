package ecs.entities;

import ecs.components.InventoryComponent;
import ecs.components.ItemComponent;
import ecs.items.Category;
import ecs.items.ItemData;
import ecs.items.ItemType;
import graphic.Animation;

import java.util.Collections;

public class Bag extends Entity{
    private static final int BAG_SIZE = 5;
    private static final String name = "Bag";
    private static final String description = "A bag which in exchange for an item slots grants several more.";
    private ItemComponent itemComponent;
    private InventoryComponent inventoryComponent;
    private ItemType itemType;

    public Bag() {
        new ItemComponent(this, new ItemData(
            ItemType.Active,
            Category.REST,
            new Animation(Collections.singleton("objects/items/bag/bag_inventory.png"), 1),
            new Animation(Collections.singleton("objects/items/bag/bag_world.png"), 1),
            name,
            description
        ));
        new InventoryComponent(this, BAG_SIZE);
    }
}

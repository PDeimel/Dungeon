package ecs.items;

import ecs.components.InventoryComponent;
import graphic.Animation;

import java.util.ArrayList;

public class Bag extends ItemData{
    private ArrayList<String> inventoryTexture = new ArrayList<>();
    private ArrayList<String> worldTexture = new ArrayList<>();
    public Bag() {
        super();
        super.setItemName("Bag");
        super.setDescription("A small backpack which, in exchange for an inventory slot, grants a few inventory slots.");
        inventoryTexture.add("objects/items/bag/bag_inventory.png");
        super.setInventoryTexture(new Animation(inventoryTexture, 5));
        worldTexture.add("objects/items/bag/bag_world.png");
        super.setWorldTexture(new Animation(worldTexture, 5));
    }
}

package ecs.items;

import graphic.Animation;

import java.util.ArrayList;

public class Earthquake extends ItemData{
    private ArrayList<String> inventoryTexture = new ArrayList<>();
    private ArrayList<String> worldTexture = new ArrayList<>();
    public Earthquake() {
        super();
        super.setItemName("Earthquake");
        super.setDescription("Shakes the whole floor and damages everyone for 10 HP. Can be used thrice.");
        inventoryTexture.add("objects/items/earthquake/earthquake_inventory.png");
        super.setInventoryTexture(new Animation(inventoryTexture, 5));
        worldTexture.add("objects/items/earthquake/earthquake_world.png");
        super.setWorldTexture(new Animation(worldTexture, 5));
    }
}

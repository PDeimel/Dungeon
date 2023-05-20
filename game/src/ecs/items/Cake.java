package ecs.items;

import graphic.Animation;

import java.util.ArrayList;

public class Cake extends ItemData{

    private ArrayList<String> inventoryTexture = new ArrayList<>();
    private ArrayList<String> worldTexture = new ArrayList<>();
    public Cake() {
        super();
        super.setItemName("Cake");
        super.setDescription("A yummy piece of cake that will restore 20 HP.");
        inventoryTexture.add("objects/items/cake/cake_inventory.png");
        super.setInventoryTexture(new Animation(inventoryTexture, 5));
        worldTexture.add("objects/items/cake/cake_world.png");
        super.setWorldTexture(new Animation(worldTexture, 5));
    }
}

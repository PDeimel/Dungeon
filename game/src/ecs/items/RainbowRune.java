package ecs.items;

import graphic.Animation;

import java.util.ArrayList;

public class RainbowRune extends ItemData {
    private ArrayList<String> inventoryTexture = new ArrayList<>();
    private ArrayList<String> worldTexture = new ArrayList<>();

    public RainbowRune() {
        super();
        super.setItemName("Rainbow-Rune");
        super.setDescription("Grants a random effect. Gambling always pays off.");
        inventoryTexture.add("objects/items/rainbowrune/rainbowrune_inventory.png");
        super.setInventoryTexture(new Animation(inventoryTexture, 5));
        worldTexture.add("objects/items/rainbowrune/rainbowrune_world.png");
        super.setWorldTexture(new Animation(worldTexture, 5));
    }
}

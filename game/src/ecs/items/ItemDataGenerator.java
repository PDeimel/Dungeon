package ecs.items;

import graphic.Animation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private List<ItemData> templates =
            List.of(
                new ItemData(
                    ItemType.Active,
                    Category.HEAL,
                    new Animation(Collections.singleton("objects/items/cake/cake_inventory.png"), 1),
                    new Animation(Collections.singleton("objects/items/cake/cake_world.png"), 1),
                    "Cake",
                    "A juicy peace of cake which heals for 20 HP.",
                    new HealEffect()
                ),
                new ItemData(
                    ItemType.Active,
                    Category.DAMAGE,
                    new Animation(Collections.singleton("objects/items/earthquake/earthquake_inventory.png"), 1),
                    new Animation(Collections.singleton("objects/items/earthquake/earthquake_world.png"), 1),
                    "Earthquake",
                    "A tremendous earthquake which damages any unit on the floor for 10 HP.",
                    new EarthquakeEffect()
                ),
                new ItemData(
                    ItemType.Active,
                    Category.REST,
                    new Animation(Collections.singleton("objects/items/rainbowrune/rainbowrune_inventory.png"), 1),
                    new Animation(Collections.singleton("objects/items/rainbowrune/rainbowrune_world.png"), 1),
                    "Rainbow-Rune",
                    "A magical stone that grants a random effect. Gambling always pays off!",
                    new RainbowEffect()
                ),
                new ItemData(
                    ItemType.Basic,
                    Category.BAG,
                    new Animation(Collections.singleton("objects/items/bag/bag_inventory.png"), 1),
                    new Animation(Collections.singleton("objects/items/bag/bag_world.png"), 1),
                    "Bag",
                    "A small bag that can hold a few additional items.",
                    5
                )
            );
    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }
}

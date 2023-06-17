package ecs.items;

import ecs.items.individualitems.*;

import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    // Every type of item created is put into the template-list
    private List<ItemData> templates =
            List.of(new Bag(), new Cake(), new Earthquake(), new RainbowRune(),new ChestKey());
    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }
}

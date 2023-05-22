package ecs.items;

import ecs.items.individualitems.Bag;
import ecs.items.individualitems.Cake;
import ecs.items.individualitems.Earthquake;
import ecs.items.individualitems.RainbowRune;

import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private List<ItemData> templates =
            List.of(
                new Bag(),
                new Cake(),
                new Earthquake(),
                new RainbowRune()
            );
    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }
}

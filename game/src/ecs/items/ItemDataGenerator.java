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

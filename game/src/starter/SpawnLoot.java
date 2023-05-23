package starter;

import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
import ecs.items.WorldItemBuilder;
import ecs.items.individualitems.Cake;
import ecs.items.individualitems.RainbowRune;
import level.IOnLevelLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * A spawnclass similar to SpawnMonsters that, for every newly loaded level, creates a bunch of items wich
 * can then be collected by the hero.
 */
public class SpawnLoot implements IOnLevelLoader {
    Entity newItem = new Entity();

    List<ItemData> chestItems = new ArrayList<>();
    Chest chest;

    public SpawnLoot() {
        newItem = WorldItemBuilder.buildWorldItem(new ItemDataGenerator().generateItemData());
        chestItems.add(new RainbowRune());
        chestItems.add(new Cake());
        chest = new Chest(chestItems, Game.currentLevel.getRandomTilePoint());
        onLevelLoad();
    }

    /**
     * Whith a specific chance an Item is added into the game
     */
    @Override
    public void onLevelLoad() {
        if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2) {
            Game.addEntity(newItem);
            Game.addEntity(chest);
        }

    }
}

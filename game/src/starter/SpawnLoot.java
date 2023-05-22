package starter;

import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.items.ItemDataGenerator;
import ecs.items.WorldItemBuilder;
import level.IOnLevelLoader;
import java.util.Random;

/**
 * A spawnclass similar to SpawnMonsters that, for every newly loaded level, creates a bunch of items wich
 * can then be collected by the hero.
 */
public class SpawnLoot implements IOnLevelLoader {
    Random rd = new Random();
    Entity newItem = new Entity();

    public SpawnLoot() {
        newItem = WorldItemBuilder.buildWorldItem(new ItemDataGenerator().generateItemData());
        onLevelLoad();
    }

    /**
     * Whith a specific chance an Item is added into the game
     */
    @Override
    public void onLevelLoad() {
        int ran = rd.nextInt(9);
        if (ran == 0) {
            Game.addEntity(newItem);
        }

    }
}

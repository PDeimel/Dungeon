package starter;

import ecs.entities.Chest;
import ecs.items.ItemDataGenerator;
import ecs.items.WorldItemBuilder;
import level.IOnLevelLoader;

/**
 * A spawnclass similar to SpawnMonsters that, for every newly loaded level, creates a bunch of
 * items wich can then be collected by the hero.
 */
public class SpawnLoot implements IOnLevelLoader {

    public SpawnLoot() {
        onLevelLoad();
    }

    /** Whith a specific chance an Item is added into the game */
    @Override
    public void onLevelLoad() {
        if ((int) Math.floor(Math.random() * (5 - 1)) == 2) {
            Game.addEntity(
                    WorldItemBuilder.buildWorldItem(new ItemDataGenerator().generateItemData()));
            Game.addEntity(Chest.createNewChest());
        }
    }
}

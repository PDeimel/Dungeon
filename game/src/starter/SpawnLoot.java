package starter;

import ecs.entities.Chest;
import ecs.entities.Entity;
import ecs.items.ItemDataGenerator;
import ecs.items.WorldItemBuilder;
import level.IOnLevelLoader;
import java.util.Random;

public class SpawnLoot implements IOnLevelLoader {
    Random rd = new Random();
    Entity newItem = new Entity();

    public SpawnLoot() {
        newItem = WorldItemBuilder.buildWorldItem(new ItemDataGenerator().generateItemData());
        onLevelLoad();
    }
    @Override
    public void onLevelLoad() {
        int ran = rd.nextInt(9);
        if (ran == 0) {
            Game.addEntity(newItem);
        }

    }
}

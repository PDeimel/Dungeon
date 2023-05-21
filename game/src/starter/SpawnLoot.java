package starter;

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
        if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2) {
            Game.addEntity(newItem);
        }

    }
}

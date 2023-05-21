package starter;

import ecs.items.ItemDataGenerator;
import ecs.items.WorldItemBuilder;
import level.IOnLevelLoader;
import java.util.Random;

public class SpawnLoot implements IOnLevelLoader {
    Random rd = new Random();

    public SpawnLoot() {
        onLevelLoad();
    }
    @Override
    public void onLevelLoad() {
        if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2) {
            WorldItemBuilder.buildWorldItem(new ItemDataGenerator().generateItemData());
        }

    }
}

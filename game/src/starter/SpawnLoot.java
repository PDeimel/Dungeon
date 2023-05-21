package starter;

import ecs.components.PositionComponent;
import ecs.entities.Chest;
import ecs.items.Earthquake;
import ecs.items.ItemData;
import ecs.items.RainbowRune;
import level.IOnLevelLoader;

import java.util.ArrayList;

public class SpawnLoot implements IOnLevelLoader {
    ArrayList<ItemData> chestItems = new ArrayList<>();

    public SpawnLoot() {
        chestItems.add(new RainbowRune());
        chestItems.add(new Earthquake());
        onLevelLoad();
    }
    @Override
    public void onLevelLoad() {
        if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2)
            new Chest(chestItems,Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint());
    }
}

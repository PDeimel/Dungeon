package ecs.items;

import ecs.entities.Entity;
import starter.SpawnMonsters;

import java.util.Random;

public class RainbowEffect implements IOnUse {
    Random rd = new Random();
    @Override
    public void onUse(Entity e, ItemData item) {
        int res = rd.nextInt(3);
        if(res == 0) {
            new HealEffect().onUse(e, item);
            System.out.println("The Rainbow-Rune healed you.");
        }
        else if(res == 1) {
            new EarthquakeEffect().onUse(e, item);
            System.out.println("The Rainbow-Rune created an earthquake.");
        }
        else {
            new SpawnMonsters(5).onLevelLoad();
            System.out.println("The Rainbow-Rune spawned many monsters.");
        }
    }
}

package starter;

import ecs.entities.*;
import level.IOnLevelLoader;

/**
 * Generates a random amount of different monsters per level, generally increasing the deeper the hero gets.
 */
public class SpawnMonsters implements IOnLevelLoader {

    private final int EASY = 10;
    private final int MEDIUM = 20;
    private final int HARD = 40;
    private final int levelReached;
    private int amountOfMonsters;
    private boolean graveSpawned = false;
    public SpawnMonsters(int levelReached) {
        this.levelReached = levelReached;
        spawnAmount();
    }

    /**
     * Spawns a random amount of different monsters depending on the levels already reached.
     */
    private void spawnAmount() {
        if(levelReached <= EASY) {
            amountOfMonsters = (int)Math.ceil(Math.random()*(5-3)+2);
            onLevelLoad();
        }
        else if(levelReached <= MEDIUM) {
            amountOfMonsters = (int)Math.ceil(Math.random()*(10-6)+5);
            onLevelLoad();
        }
        else if(levelReached <= HARD) {
            amountOfMonsters = (int)Math.ceil(Math.random()*(15-11)+10);
            onLevelLoad();
        }
    }


    @Override
    public void onLevelLoad() {
        for(int i = 0; i < (double) (amountOfMonsters / 3); i++) {
            Monster m = new WormMonster();
        }
        for(int i = 0; i < (double) (amountOfMonsters / 3); i++) {
            Monster m = new PigMonster();
        }
        for(int i = 0; i < (double) (amountOfMonsters / 3); i++) {
            Monster m = new BatMonster();
        }
        if(!graveSpawned) {
            // In about 20% of new levels a ghost and his gravestone spawn
            if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2) {
                Monster ghost = new Gravestone(new Ghost((Hero) Game.getHero().orElseThrow()));
                /*  When there already is a gravestone in the level, the punishment of the
                    ghost in form of spawning monsters shall not create another stone with
                    another ghost. */
                graveSpawned = true;
            }
        }
    }

    public void setAmountOfMonsters(int amountOfMonsters) {
        this.amountOfMonsters = amountOfMonsters;
    }
}

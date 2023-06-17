package starter;

import ecs.entities.*;
import level.IOnLevelLoader;

/**
 * Generates a random amount of different monsters per level, generally increasing the deeper the
 * hero gets.
 */
public class SpawnCharacters implements IOnLevelLoader {

    int EASY = 10;
    int MEDIUM = 20;
    int HARD = 40;
    private final int levelReached;
    private int amountOfMonsters;
    private boolean graveSpawned = false;
    private boolean riddlerSpawned = false;

    public SpawnCharacters(int levelReached) {
        this.levelReached = levelReached;
        spawnAmount();
    }

    /** Spawns a random amount of different monsters depending on the levels already reached. */
    private void spawnAmount() {

        if (levelReached <= EASY) {
            amountOfMonsters = (int) Math.ceil(Math.random() * (5 - 3) + 2);
            onLevelLoad();
        } else if (levelReached <= MEDIUM) {
            amountOfMonsters = (int) Math.ceil(Math.random() * (10 - 6) + 5);
            onLevelLoad();
        } else if (levelReached <= HARD) {
            amountOfMonsters = (int) Math.ceil(Math.random() * (15 - 11) + 10);
            onLevelLoad();
        }
    }

    /**
     * Spawns an amount of monsters scaling with the depth the hero has reached and possibly creates
     * a gravestone with a ghost once per level. Additionally, a Riddleman can also be spawned.
     */
    @Override
    public void onLevelLoad() {
        for (int i = 0; i < (double) (amountOfMonsters / 3); i++) {
            Monster m = new WormMonster();
        }
        for (int i = 0; i < (double) (amountOfMonsters / 3); i++) {
            Monster m = new PigMonster();
        }
        for (int i = 0; i < (double) (amountOfMonsters / 3); i++) {
            Monster m = new BatMonster();
        }
        if (!graveSpawned) {
            // In about 20% of new levels a ghost and his gravestone spawn
            if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2) {
                NPC ghost = new Gravestone(new Ghost((Hero) Game.getHero().orElseThrow()));

                /*  When there already is a gravestone in the level, the punishment of the
                ghost in form of spawning monsters shall not create another stone with
                another ghost. */
                graveSpawned = true;
            }
        }
        /*  The riddler is able to spawn new monsters, so in order to prevent him from duping
           himself a checkup is inserted before he is created.
        */
        if (!riddlerSpawned) {
            if ((int) Math.floor(Math.random() * (5 - 1) + 0) == 2) {
                NPC riddler = new Riddleman();
            }
            riddlerSpawned = true;
        }
    }

    public void setAmountOfMonsters(int amountOfMonsters) {
        this.amountOfMonsters = amountOfMonsters;
    }
}

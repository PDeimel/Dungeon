package ecs.entities;

import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.PassiveTransition;
import starter.Game;
import starter.SpawnCharacters;
import java.util.Optional;
import java.util.Set;

/**
 * A harmless ghost that follows the hero around most of the time but is sometimes
 * able to move around freely or disappear completely from the level. Once he is in close
 * reach of his gravestone, he will leave the level behind.
 */
public class Ghost extends NPC{

    private final String pathToIdleLeft = "character/monster/ghost/idleAndRunLeft";
    private final String pathToIdleRight = "character/monster/ghost/idleAndRunRight";
    private final float XSPEED = 0.25f;
    private final float YSPEED = 0.25f;
    private boolean activeThisLevel = false;
    private final Hero hero;
    public Ghost(Hero hero) {
        super();
        this.hero = hero;
        super.setupPositionComponent();
        super.setupAnimationComponent(pathToIdleLeft, pathToIdleRight);
        super.setupVelocityComponent(XSPEED, YSPEED, pathToIdleLeft, pathToIdleRight);
        setupHitboxComponent();
        getIdle();
    }

    /**
     * When the current ghost is one that follows the hero, with a 50/50 chance it is decided whether the hero receives some
     * extra health or gets punished in the form of a few monsters. Afterwards the ghost is deleted from the level.
     */
    public void graveLoot() {
        if(activeThisLevel) {
            if(Math.round(Math.random()) == 0) {
                Optional<Component> heroHealthThis = this.hero.getComponent(HealthComponent.class);
                HealthComponent heroHealth = (HealthComponent) heroHealthThis.orElseThrow();
                heroHealth.setCurrentHealthpoints(heroHealth.getCurrentHealthpoints() + 20);
                System.out.println("The hero just received 20 health for helping the ghost reach the afterlife!");
            }
            else {
                System.out.println("The ghost is angry at the hero for making him leave his realm and punishes him!");
                SpawnCharacters punish = new SpawnCharacters(0);
                punish.setAmountOfMonsters(1);
                punish.onLevelLoad();
            }

            Set<Entity> allEntities = Game.getEntities();
            for (Entity allEntity : allEntities) {
                if(allEntity instanceof Ghost) Game.removeEntity(allEntity);
            }
        }
    }

    /**
     * With a slight chance each ghost can be unable to follow the hero in the current level.
     * If that is the case, the hero shall not receive anything from neither ghost nor grave.
     */
    private void getIdle() {
        if((int)Math.floor(Math.random()*(15-1)+0) == 1) {
            System.out.println("Ghost is idle");
            setUpPassiveAITransition();
        }
        else {
            activeThisLevel = true;
            new AIComponent(this);
            System.out.println("Ghost follows");
        }
    }


    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (player, ghost, direction) -> {},
            (player, ghost, direction) -> {});
    }

    /**
     * Gives the ghost a special AI in which it well never try to follow or attack the hero.
     */
    private void setUpPassiveAITransition() {
        new AIComponent(this, new CollideAI(3f), new StaticRadiusWalk(15f, 2), new PassiveTransition());
    }
}

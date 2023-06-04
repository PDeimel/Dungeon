package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.idle.StaticRadiusWalk;
import ecs.components.ai.transition.PassiveTransition;
import graphic.Animation;
import java.util.Optional;
import java.util.Set;
import starter.Game;
import starter.SpawnMonsters;

/**
 * A harmless ghost that follows the hero around most of the time but is sometimes able to move
 * around freely or disappear completely from the level. Once he is in close reach of his
 * gravestone, he will leave the level behind.
 */
public class Ghost extends Monster {

    private boolean activeThisLevel = false;
    private final Hero hero;

    public Ghost(Hero hero) {
        super();
        super.setxSpeed(0.25f);
        super.setySpeed(0.25f);
        super.setPathToIdleLeft("character/monster/ghost/idleAndRunLeft");
        super.setPathToIdleRight("character/monster/ghost/idleAndRunRight");
        super.setPathToRunLeft("character/monster/ghost/idleAndRunLeft");
        super.setPathToRunRight("character/monster/ghost/idleAndRunRight");
        this.hero = hero;
        super.setIdleLeft();
        super.setIdleRight();
        super.setDmg(0);
        setUpPositionComponent();
        setUpAnimationComponent();
        setUpVelocityComponent();
        setUpHitboxComponent();
        getIdle();
    }

    /**
     * When the current ghost is one that follows the hero, with a 50/50 chance it is decided
     * whether the hero receives some extra health or gets punished in the form of a few monsters.
     * Afterwards the ghost is deleted from the level.
     */
    public void graveLoot() {
        if (activeThisLevel) {
            if (Math.round(Math.random()) == 0) {
                Optional<Component> heroHealthThis = this.hero.getComponent(HealthComponent.class);
                HealthComponent heroHealth = (HealthComponent) heroHealthThis.orElseThrow();
                heroHealth.setCurrentHealthpoints(heroHealth.getCurrentHealthpoints() + 20);
                System.out.println(
                        "The hero just received 20 health for helping the ghost reach the afterlife!");
            } else {
                System.out.println(
                        "The ghost is angry at the hero for making him leave his realm and punishes him!");
                SpawnMonsters punish = new SpawnMonsters(0);
                punish.setAmountOfMonsters(1);
                punish.onLevelLoad();
            }

            Set<Entity> allEntities = Game.getEntities();
            for (Entity allEntity : allEntities) {
                if (allEntity instanceof Ghost) Game.removeEntity(allEntity);
            }
        }
    }

    /**
     * With a slight chance each ghost can be unable to follow the hero in the current level. If
     * that is the case, the hero shall not receive anything from neither ghost nor grave.
     */
    private void getIdle() {
        if ((int) Math.floor(Math.random() * (15 - 1) + 0) == 1) {
            System.out.println("Ghost is idle");
            setUpPassiveAITransition();
        } else {
            activeThisLevel = true;
            new AIComponent(this);
            System.out.println("Ghost follows");
        }
    }

    private void setUpAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(getPathToIdleRight());
        Animation idleLeft = AnimationBuilder.buildAnimation(getPathToIdleLeft());
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setUpVelocityComponent() {
        Animation runRight = AnimationBuilder.buildAnimation(getPathToRunRight());
        Animation runLeft = AnimationBuilder.buildAnimation(getPathToRunLeft());
        new VelocityComponent(this, getxSpeed(), getySpeed(), runLeft, runRight);
    }

    private void setUpHitboxComponent() {
        new HitboxComponent(
                this,
                (player, ghost, direction) -> System.out.println("Collides with ghost"),
                (player, ghost, direction) -> System.out.println("Leaves ghost collision"));
    }

    private void setUpPositionComponent() {
        new PositionComponent(this);
    }

    /** Gives the ghost a special AI in which it well never try to follow or attack the hero. */
    private void setUpPassiveAITransition() {
        new AIComponent(
                this, new CollideAI(3f), new StaticRadiusWalk(15f, 2), new PassiveTransition());
    }
}

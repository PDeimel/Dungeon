package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.HealthComponent;
import ecs.components.collision.MonsterCollisionEnter;
import ecs.components.collision.MonsterCollisionOut;
import ecs.components.xp.XPComponent;
import graphic.Animation;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import starter.Game;

/**
 * An abstract class used to create and distinct different monsters which will be obstacles to the
 * hero.
 */
public abstract class Monster extends Entity {

    private float xSpeed;
    private float ySpeed;
    private String pathToIdleLeft;
    private String pathToIdleRight;
    private String pathToRunLeft;
    private String pathToRunRight;
    private Animation idleLeft;
    private Animation idleRight;
    private MonsterCollisionEnter monsterCollisionEnter = new MonsterCollisionEnter();
    private MonsterCollisionOut monsterCollisionOut = new MonsterCollisionOut();
    Animation missingTextureAnimation = new Animation(List.of("animation/missingTexture.png"), 100);
    private int dmg;
    private Logger monsterLogger = Logger.getLogger(this.getClass().getName());

    public float getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public float getySpeed() {
        return ySpeed;
    }

    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public String getPathToIdleLeft() {
        return pathToIdleLeft;
    }

    public void setPathToIdleLeft(String pathToIdleLeft) {
        this.pathToIdleLeft = pathToIdleLeft;
    }

    public String getPathToIdleRight() {
        return pathToIdleRight;
    }

    public void setPathToIdleRight(String pathToIdleRight) {
        this.pathToIdleRight = pathToIdleRight;
    }

    public String getPathToRunLeft() {
        return pathToRunLeft;
    }

    public void setPathToRunLeft(String pathToRunLeft) {
        this.pathToRunLeft = pathToRunLeft;
    }

    public String getPathToRunRight() {
        return pathToRunRight;
    }

    public void setPathToRunRight(String pathToRunRight) {
        this.pathToRunRight = pathToRunRight;
    }

    public Animation getIdleLeft() {
        return idleLeft;
    }

    public void setIdleLeft() {
        this.idleLeft = AnimationBuilder.buildAnimation(getPathToIdleLeft());
    }

    public Animation getIdleRight() {
        return idleRight;
    }

    public void setIdleRight() {
        this.idleRight = AnimationBuilder.buildAnimation(getPathToIdleRight());
    }

    public MonsterCollisionEnter getMonsterCollisionEnter() {
        return monsterCollisionEnter;
    }

    public MonsterCollisionOut getMonsterCollisionOut() {
        return monsterCollisionOut;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    /**
     * Enables the monster to die and once that happens it transfers it's loot-Xp
     * to the hero's XPComponent.
     *
     * @param health The HP of the monster
     */
    public void setUpHealthComponent(int health) {
        new HealthComponent(
                this,
                health,
                (Entity e) -> {
                    Game.getHero()
                        .get()
                        .getComponent(XPComponent.class)                // Gets the XPComponent of the hero
                        .ifPresent(xpc ->
                            ((XPComponent) xpc).addXP(getLootXP()));    // Adds monster's XP to hero's
                    HealthComponent hc =
                            (HealthComponent) e.getComponent(HealthComponent.class).orElseThrow();
                },
                missingTextureAnimation,
                missingTextureAnimation);
    }

    /**
     * Gives the monster an XPComponent, to use it's lootXP-Parameter
     *
     * @param lootXP The XP the entity grants when killed
     */
    public void setUpXPComponent(int lootXP) {
        new XPComponent(this, (long nextLevel) -> {}, lootXP);
    }

    /**
     * Get the LootXP out of the XPComponent to avoid cramming in the onDeathFunction
     * in setUpHealthComponent.
     *
     * @return The monster's lootXP
     */
    private long getLootXP() {
        AtomicLong xp = new AtomicLong();
        this.getComponent(XPComponent.class).ifPresent(xpc -> {
            xp.set(((XPComponent) xpc).getLootXP());
        });
        return xp.get();
    }
}

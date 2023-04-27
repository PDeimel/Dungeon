package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.TrapCollisionEnter;
import ecs.components.collision.TrapCollisionOut;
import graphic.Animation;

/**
 * Creates a Spike-Trap entity which will hurt the hero once he steps on it
 */
public class Trap extends Entity{

    private TrapCollisionOut trapCollisionOut = new TrapCollisionOut();
    private TrapCollisionEnter trapCollisionEnter = new TrapCollisionEnter();
    private Animation trapActive = AnimationBuilder.buildAnimation("character/trap.spikeTrap/spikeTrap_active.png");
    private Animation trapHidden = AnimationBuilder.buildAnimation("character/trap.spikeTrap/spikeTrap_inactive.png");



    public Trap(){

        new HitboxComponent(this,trapCollisionEnter, trapCollisionOut);
        new PositionComponent(this);
        new AnimationComponent(this,trapHidden);

    }
}

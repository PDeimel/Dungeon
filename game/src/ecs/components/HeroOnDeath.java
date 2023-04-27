package ecs.components;

import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;
import starter.GameOverScreen;

/**
 * Is used once the hero's health depletes to 0 and creates a new GameOver-screen.
 */
public class HeroOnDeath implements IOnDeathFunction {
    private final float XSPEED = 0.0f;
    private final float YSPEED = 0.0f;

    /**
     * Once the hero dies, he can't move anymore and the Game Over Screen opens up.
     *
     * @param entity Entity that has died
     */
    @Override
    public void onDeath(Entity entity) {
        if(entity instanceof Hero){
            VelocityComponent v = (VelocityComponent) entity.getComponent(VelocityComponent.class).orElse(null);
            assert v != null;
            v.setCurrentXVelocity(XSPEED);
            v.setCurrentYVelocity(YSPEED);
            Game.togglePause();
            GameOverScreen g = new GameOverScreen();
        }
    }
}

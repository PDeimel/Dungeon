package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class BodyAttackSkill implements ISkillFunction {
    /**
     * @pathToTexturesOfProjectile has the Animation for witch position of Hero in Attack mode
     * @damage the object to apply damage amount in HealthComponent
     * @hitboxSize the area of contact
     */
    private Damage damage;

    private Point hitboxSize;
    private String pathToAttackAnimation;
    Animation animation = AnimationBuilder.buildAnimation("character/knight/idleLeft");
    Animation animation2 = AnimationBuilder.buildAnimation("character/knight/idleRight");

    public BodyAttackSkill(Damage damage, Point point, String pathToAttackAnimation) {
        this.damage = damage;
        this.hitboxSize = point;
        this.pathToAttackAnimation = pathToAttackAnimation;
    }

    @Override

    /**
     * create an entity and uses it as attack
     *
     * <p>Create one entity with the necessary componets to realize an attack the attack happens
     * only near to hero, the position of hero is the same position of attack @Entity who uses the
     * attack
     */
    public void execute(Entity entity) {
        Entity attack = new Entity();

        PositionComponent pc =
                (PositionComponent) entity.getComponent(PositionComponent.class).orElseThrow();
        new PositionComponent(attack, pc.getPosition());

        new BodyAttackComponent(attack);

        Animation attackAnimation = AnimationBuilder.buildAnimation(pathToAttackAnimation);
        new AnimationComponent(entity, attackAnimation);

        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(damage);
                                            // Change back to original animation
                                            new AnimationComponent(entity, animation, animation2);
                                            Game.removeEntity(attack);
                                        });
                    } else {
                        // Change back to original animation
                        new AnimationComponent(entity, animation);
                        Game.removeEntity(attack);
                    }
                };

        new HitboxComponent(attack, new Point(0.25f, 0.25f), hitboxSize, collide, null);
    }
}

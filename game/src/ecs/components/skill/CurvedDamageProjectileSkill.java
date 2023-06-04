package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.components.ProjectileTag;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class CurvedDamageProjectileSkill implements ISkillFunction {

    private String pathToTexturesOfProjectile;
    private float projectileSpeed;
    private float projectileRange;
    private Damage projectileDamage;
    private Point projectileHitboxSize;
    private ITargetSelection selectionFunction;

    public CurvedDamageProjectileSkill(
            String pathToTexturesOfProjectile,
            float projectileSpeed,
            Damage projectileDamage,
            Point projectileHitboxSize,
            ITargetSelection selectionFunction,
            float projectileRange) {
        this.pathToTexturesOfProjectile = pathToTexturesOfProjectile;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;
        this.projectileRange = projectileRange;
        this.projectileHitboxSize = projectileHitboxSize;
        this.selectionFunction = selectionFunction;
    }

    /**
     * create an entity witch is used as projectile
     *
     * The Entity receives all components that are necessary to represent a Projectile uses the
     * new Constructor for ProjectileComponent to determinate if the Projectile is curved or not
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {
        Entity projectile = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
        new AnimationComponent(projectile, animation);

        Point aimedOn = selectionFunction.selectTargetPoint();
        Point targetPoint =
                SkillTools.calculateLastPositionInRange(
                        epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
                SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);

        new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        /**
         * Constructor of Projectile Component receives  true parameter , Projectile turns curved(
         * Variable used in
         * ProjectileSystem)
         */
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint, true);

        ICollide collide =
                (a, b, from) -> {
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            Game.removeEntity(projectile);
                                        });
                    }
                };

        new HitboxComponent(
                projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);

        new ProjectileTag(projectile);
    }
}

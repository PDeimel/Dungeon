package ecs.components.collision;

import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.components.skill.ProjectileComponent;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Point;

import java.util.List;

public class MonsterCollisionEnter implements ICollide {

    private static final float MAX_DISTANCE = 0.25f;


    /**
     * Method filter The Entities that are in Collision, both have a HitboxComponent
     * a small distance is given to PositionComponent of Monster.
     *
     *
     * @param a is the current Entity
     * @param b is the Entity with whom the Collision happened
     * @param from the direction from a to b
     */

    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        Entity monster = null;
        Entity projectile = null;

        if (a.getComponent(HealthComponent.class).isPresent() && b.getComponent(ProjectileComponent.class).isPresent()) {
            monster = a;
            projectile = b;
        } else if (a.getComponent(ProjectileComponent.class).isPresent() && b.getComponent(HealthComponent.class).isPresent()) {
            monster = b;
            projectile = a;
        }

        if (monster != null && projectile != null) {
            PositionComponent monsterPosition = (PositionComponent) monster.getComponent(PositionComponent.class)
                .orElseThrow(() -> new IllegalStateException("Monster entity does not have a position component"));

            Point projectilePosition = ((PositionComponent) projectile.getComponent(PositionComponent.class)
                .orElseThrow(() -> new IllegalStateException("Projectile entity does not have a position component")))
                .getPosition();

            Tile farthestAccessibleTile = findFarthestAccessibleTile(monsterPosition.getPosition(), projectilePosition, MAX_DISTANCE);

            if (farthestAccessibleTile != null) {
                monsterPosition.setPosition(farthestAccessibleTile.getCoordinateAsPoint());
            }
        }


    }
    // Method used to find the farthest File to Monster, once a Monster receive a hit with Projectile
    //

    private Tile findFarthestAccessibleTile(Point monsterPosition, Point projectilePosition, float maxDistance) {
        List<Tile> accessibleTiles = AITools.getAccessibleTilesInRange(monsterPosition, maxDistance);

        Tile farthestAccessibleTile = null;
        float maxDistanceFound = 0;
        for (Tile tile : accessibleTiles) {
            Point tilePosition = tile.getCoordinateAsPoint();
            float distance = Point.calculateDistance(tilePosition, projectilePosition);
            if (distance > maxDistanceFound) {
                maxDistanceFound = distance;
                farthestAccessibleTile = tile;
            }
        }

        return farthestAccessibleTile;
    }
    }



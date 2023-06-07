package ecs.components.collision;

import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.components.skill.BodyAttackComponent;
import ecs.components.skill.ProjectileComponent;
import ecs.entities.Entity;
import java.util.List;
import level.elements.tile.Tile;
import tools.Point;

public class MonsterCollisionEnter implements ICollide {

    private static final float MAX_DISTANCE = 0.1f;

    /**
     * Handles a collision between two entities. If one of the entities is a monster and the other
     * is an attacker (with a projectile or body attack component), damage and knockback is applied
     * to the monster.
     *
     * @param a the first entity involved in the collision
     * @param b the second entity involved in the collision
     * @param from the direction from the first entity to the second
     */
    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {
        Entity monster = null;
        Entity attacker = null;

        if (a.getComponent(HealthComponent.class).isPresent()
                && (b.getComponent(ProjectileComponent.class).isPresent()
                        || b.getComponent(BodyAttackComponent.class).isPresent())) {
            monster = a;
            attacker = b;
        } else if ((a.getComponent(ProjectileComponent.class).isPresent()
                        || a.getComponent(BodyAttackComponent.class).isPresent())
                && b.getComponent(HealthComponent.class).isPresent()) {
            monster = b;
            attacker = a;
        }

        if (monster != null && attacker != null) {
            PositionComponent monsterPosition =
                    (PositionComponent)
                            monster.getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new IllegalStateException(
                                                            "Monster entity does not have a position component"));

            Point attackerPosition =
                    ((PositionComponent)
                                    attacker.getComponent(PositionComponent.class)
                                            .orElseThrow(
                                                    () ->
                                                            new IllegalStateException(
                                                                    "Attacker entity does not have a position component")))
                            .getPosition();

            Tile farthestAccessibleTile =
                    findFarthestAccessibleTile(
                            monsterPosition.getPosition(), attackerPosition, MAX_DISTANCE);

            if (farthestAccessibleTile != null) {
                monsterPosition.setPosition(farthestAccessibleTile.getCoordinateAsPoint());
            }
        }
    }

    /**
     * Finds the farthest accessible tile from the monster's position, within a specified maximum
     * distance. This tile represents the position where the monster will be moved to when it
     * receives an attack.
     *
     * @param monsterPosition the current position of the monster
     * @param attackPosition the position of the attacker
     * @param maxDistance the maximum distance to look for an accessible tile
     * @return the farthest accessible tile, or null if none was found
     */
    private Tile findFarthestAccessibleTile(
            Point monsterPosition, Point attackPosition, float maxDistance) {
        List<Tile> accessibleTiles =
                AITools.getAccessibleTilesInRange(monsterPosition, maxDistance);

        Tile farthestAccessibleTile = null;
        float maxDistanceFound = 0;
        for (Tile tile : accessibleTiles) {
            Point tilePosition = tile.getCoordinateAsPoint();
            float distance = Point.calculateDistance(tilePosition, attackPosition);
            if (distance > maxDistanceFound) {
                maxDistanceFound = distance;
                farthestAccessibleTile = tile;
            }
        }
        return farthestAccessibleTile;
    }
}

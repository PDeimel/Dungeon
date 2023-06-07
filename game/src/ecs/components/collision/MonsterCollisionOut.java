package ecs.components.collision;

import ecs.entities.Entity;
import level.elements.tile.Tile;

public class MonsterCollisionOut implements ICollide {
    @Override
    public void onCollision(Entity a, Entity b, Tile.Direction from) {}
}

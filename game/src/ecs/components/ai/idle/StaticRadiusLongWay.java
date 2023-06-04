package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import java.util.List;
import level.elements.tile.Tile;
import tools.Constants;
import tools.Point;

public class StaticRadiusLongWay implements IIdleAI {

    private final float radius;
    private GraphPath<Tile> path;
    private final int breakTime;
    private int currentBreak = 0;
    private Point center;
    private Point currentPosition;

    public StaticRadiusLongWay(float radius, int breakTimeInSeconds) {
        this.radius = radius;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
    }

    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            if (center == null) {
                PositionComponent pc =
                        (PositionComponent)
                                entity.getComponent(PositionComponent.class).orElseThrow();
                center = pc.getPosition();
            }

            if (currentBreak >= breakTime) {
                currentBreak = 0;
                PositionComponent pc2 =
                        (PositionComponent)
                                entity.getComponent(PositionComponent.class).orElseThrow();
                currentPosition = pc2.getPosition();
                List<Tile> accessibleTiles = AITools.getAccessibleTilesInRange(center, radius);

                double maxDistance = 0;
                Tile farthestTile = null;
                for (Tile tile : accessibleTiles) {
                    double distance =
                            Point.calculateDistance(center, tile.getCoordinate().toPoint());
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        farthestTile = tile;
                    }
                }

                if (farthestTile != null) {
                    path =
                            AITools.calculatePath(
                                    currentPosition.toCoordinate(), farthestTile.getCoordinate());
                } else {
                    path = null;
                }
                idle(entity);
            }
            currentBreak++;
        } else AITools.move(entity, path);
    }
}

package ecs.components.skill;

import ecs.components.Component;
import ecs.entities.Entity;
import tools.Point;

public class ProjectileComponent extends Component {

    private boolean isCurved; // // used to determinate ,if the projectile will fly curved.

    private Point goalLocation;
    private Point startPosition;

    public ProjectileComponent(
            Entity entity, Point startPosition, Point goalLocation, boolean isCurved) {
        super(entity);
        this.goalLocation = goalLocation;
        this.startPosition = startPosition;
        this.isCurved = isCurved;
    }

    /**
     * gets the goal position of the projectile
     *
     * @return goal position of the projectile
     */
    public Point getGoalLocation() {
        return goalLocation;
    }

    /**
     * gets the start position of the projectile
     *
     * @return start position of the projectile
     */
    public Point getStartPosition() {
        return startPosition;
    }

    public boolean isCurving() {
        return isCurved;
    }
}

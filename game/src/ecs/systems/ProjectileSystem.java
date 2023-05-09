package ecs.systems;

import ecs.components.*;
import ecs.components.skill.ProjectileComponent;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

public class ProjectileSystem extends ECS_System {

    // private record to hold all data during streaming



    private record PSData(
            Entity e, ProjectileComponent prc, PositionComponent pc, VelocityComponent vc) {}

    /** sets the velocity and removes entities that reached their endpoint */
    @Override
    public void update() {
        Game.getEntities().stream()

                .flatMap(e -> e.getComponent(ProjectileComponent.class).stream())
                .map(prc -> buildDataObject((ProjectileComponent) prc))
                .map(this::setVelocity)
                .map(this::updateProjectilePosition)

                .filter(
                        psd ->
                                hasReachedEndpoint(
                                        psd.prc.getStartPosition(),
                                        psd.prc.getGoalLocation(),
                                        psd.pc.getPosition()))

                .forEach(this::removeEntitiesOnEndpoint);
    }

    private PSData buildDataObject(ProjectileComponent prc) {
        Entity e = prc.getEntity();

        PositionComponent pc =
                (PositionComponent)
                        e.getComponent(PositionComponent.class)
                                .orElseThrow(ProjectileSystem::missingAC);
        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(ProjectileSystem::missingAC);

        return new PSData(e, prc, pc, vc);

    }

        /**
         Method aplly curve to Projectile with changes in y or x Achse, or change the direction with changes  only on Y Achse
         verify, if the velocity in both Achses y und x to decide where will apply the decay factor
         Verify, if the traveled distance of projectile bigger or smaller
         if bigger, we change the values of velocity in Y-Achse to negative and then we have other tipe of Movement
         after a determinate distance
         */

    private PSData updateProjectilePosition(PSData data) {
        if (data.prc.isCurving()) {
            float decayFactor = 0.5f;
            float distanceFromStart = Point.calculateDistance(data.pc.getPosition(), data.prc.getStartPosition());


            if (Math.abs(data.vc.getXVelocity()) > Math.abs(data.vc.getYVelocity())) {

                data.pc.getPosition().y -= decayFactor * distanceFromStart;
            } else {

                data.pc.getPosition().x -= decayFactor * distanceFromStart;
            }
        } else {

            float distance= 2.5f; // Abstand um die Richtung zu ändern
            float distanceFromStart = Point.calculateDistance(data.pc.getPosition(), data.prc.getStartPosition());
            if (distanceFromStart > distance) {
                data.vc.setCurrentYVelocity(-Math.abs(data.vc.getYVelocity()));
            }
        }

        return data;
    }

    private PSData setVelocity(PSData data) {
        data.vc.setCurrentYVelocity(data.vc.getYVelocity());
        data.vc.setCurrentXVelocity(data.vc.getXVelocity());

        return data;
    }

    private void removeEntitiesOnEndpoint(PSData data) {
        Game.removeEntity(data.pc.getEntity());
    }

    /**
     * checks if the endpoint is reached
     *
     * @param start position to start the calculation
     * @param end point to check if projectile has reached its goal
     * @param current current position
     * @return true if the endpoint was reached or passed, else false
     */
    public boolean hasReachedEndpoint(Point start, Point end, Point current) {
        float dx = start.x - current.x;
        float dy = start.y - current.y;
        double distanceToStart = Math.sqrt(dx * dx + dy * dy);

        dx = start.x - end.x;
        dy = start.y - end.y;
        double totalDistance = Math.sqrt(dx * dx + dy * dy);

        if (distanceToStart > totalDistance) {
            // The point has reached or passed the endpoint
            return true;
        } else {
            // The point has not yet reached the endpoint
            return false;
        }
    }

    private static MissingComponentException missingAC() {
        return new MissingComponentException("AnimationComponent");
    }


}

package ecs.components.xp;

public interface ILevelUp {

    /**
     * Implements the LevelUp behavior of a XPComponent having entity
     *
     * @param nextLevel is the new level of the entity
     */
    void onLevelUp(long nextLevel);
}

package ecs.systems;

import com.badlogic.gdx.Gdx;
import configuration.KeyboardConfig;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.tools.interaction.InteractionTool;
import starter.Game;

/** Used to control the player */
public class PlayerSystem extends ECS_System {

    private record KSData(Entity e, PlayableComponent pc, VelocityComponent vc) {}

    private boolean openInv = false;
    private boolean openBag = false;
    private int invItemSlotCt = 0;
    private int bagItemSlotCt = 0;

    @Override
    public void update() {
        Game.getEntities().stream()
                .flatMap(e -> e.getComponent(PlayableComponent.class).stream())
                .map(pc -> buildDataObject((PlayableComponent) pc))
                .forEach(this::checkKeystroke);
    }

    private void checkKeystroke(KSData ksd) {
        if(!openInv) {
            if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_UP.get()))
                ksd.vc.setCurrentYVelocity(1 * ksd.vc.getYVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_DOWN.get()))
                ksd.vc.setCurrentYVelocity(-1 * ksd.vc.getYVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_RIGHT.get()))
                ksd.vc.setCurrentXVelocity(1 * ksd.vc.getXVelocity());
            else if (Gdx.input.isKeyPressed(KeyboardConfig.MOVEMENT_LEFT.get()))
                ksd.vc.setCurrentXVelocity(-1 * ksd.vc.getXVelocity());

            if (Gdx.input.isKeyPressed(KeyboardConfig.INTERACT_WORLD.get()))
                InteractionTool.interactWithClosestInteractable(ksd.e);
            else if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                openInv = true;
                System.out.println("Inventory open");
            }

            // check skills
            else if (Gdx.input.isKeyPressed(KeyboardConfig.FIRST_SKILL.get()))
                ksd.pc.getSkillSlot1().ifPresent(skill -> skill.execute(ksd.e));
            else if (Gdx.input.isKeyPressed(KeyboardConfig.SECOND_SKILL.get()))
                ksd.pc.getSkillSlot2().ifPresent(skill -> skill.execute(ksd.e));
        } else if(!openBag) {
            if (Gdx.input.isKeyJustPressed(KeyboardConfig.INVENTORY_OPEN.get())) {
                openInv = false;
                System.out.println("Inventory closed");
            }

        }
    }

    private KSData buildDataObject(PlayableComponent pc) {
        Entity e = pc.getEntity();

        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(PlayerSystem::missingVC);

        return new KSData(e, pc, vc);
    }

    private static MissingComponentException missingVC() {
        return new MissingComponentException("VelocityComponent");
    }
}

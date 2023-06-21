package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/** A Monster which acts like a chest until the hero tries to open it */
public class ChestMonster extends Monster {

    private final float XSPEED = 0.2f;
    private final float YSPEED = 0.2f;
    private final int HEALTH = 30;
    private final int LOOTXP = 200;
    private final int DMG = 10;
    public static final float defaultInteractionRadius = 1f;
    private IFightAI iFightAI = new CollideAI(0.2f);
    private IIdleAI idleAI = new PatrouilleWalk(5f, 10, 20, PatrouilleWalk.MODE.RANDOM);
    private ITransition transition = new RangeTransition(4f);
    private final Logger mimicLogger = Logger.getLogger(this.getClass().getSimpleName());

    public ChestMonster() {
        super();
        new PositionComponent(this);
        new AnimationComponent(
                this,
                AnimationBuilder.buildAnimation(
                        "objects/treasurechest/treasurechest/chest_full_open_anim_f0.png"));
        Random random = new Random();
        ItemDataGenerator itemDataGenerator = new ItemDataGenerator();

        List<ItemData> itemData =
                IntStream.range(0, random.nextInt(1, 3))
                        .mapToObj(i -> itemDataGenerator.generateItemData())
                        .toList();
        InventoryComponent ic = new InventoryComponent(this, itemData.size());
        itemData.forEach(ic::addItem);
        new InteractionComponent(this, defaultInteractionRadius, false, this::activateMonster);
        setUpXPComponent(LOOTXP);

        super.setPathToIdleLeft("character/monster/chestmonster/idleAndRunLeft");
        super.setPathToIdleRight("character/monster/chestmonster/idleAndRunRight");
        super.setPathToRunLeft("character/monster/chestmonster/idleAndRunLeft");
        super.setPathToRunRight("character/monster/chestmonster/idleAndRunRight");
        mimicLogger.info("Mimic has been spawned");
    }

    /**
     * Activates the monster-part of the mimic once interacted with
     *
     * @param entity The entity which changes
     */
    private void activateMonster(Entity entity) {
        super.setIdleRight();
        super.setIdleLeft();
        new AnimationComponent(entity, super.getIdleLeft(), super.getIdleRight());
        new HitboxComponent(
                entity, super.getMonsterCollisionEnter(), super.getMonsterCollisionOut());
        new VelocityComponent(entity, XSPEED, YSPEED, super.getIdleLeft(), super.getIdleRight());
        new HealthComponent(
                entity, HEALTH, Chest::dropItems, missingTextureAnimation, missingTextureAnimation);
        new AIComponent(entity, iFightAI, idleAI, transition);
        super.setDmg(DMG);
        mimicLogger.info("Mimic has been activated");
    }
}

package ecs.entities;

import ecs.components.*;
import ecs.components.xp.XPComponent;
import ecs.items.ItemData;
import ecs.items.ItemDataGenerator;
import ecs.items.individualitems.ChestKey;
import graphic.Animation;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import level.tools.LevelElement;
import starter.Game;
import starter.LockPickingGame;
import tools.Point;

public class Chest extends Entity {

    public static final float defaultInteractionRadius = 1f;
    public static final List<String> DEFAULT_CLOSED_ANIMATION_FRAMES =
            List.of("objects/treasurechest/treasurechest/chest_full_open_anim_f0.png");
    public static final List<String> DEFAULT_OPENING_ANIMATION_FRAMES =
            List.of(
                    "objects/treasurechest/treasurechest/chest_full_open_anim_f0.png",
                    "objects/treasurechest/treasurechest/chest_full_open_anim_f1.png",
                    "objects/treasurechest/treasurechest/chest_full_open_anim_f2.png",
                    "objects/treasurechest/treasurechest/chest_empty_open_anim_f2.png");

    /**
     * small Generator which uses the Item#ITEM_REGISTER
     *
     * @return a configured Chest
     */
    public static Chest createNewChest() {
        Random random = new Random();
        ItemDataGenerator itemDataGenerator = new ItemDataGenerator();

        List<ItemData> itemData =
                IntStream.range(0, random.nextInt(1, 3))
                        .mapToObj(i -> itemDataGenerator.generateItemData())
                        .toList();
        return new Chest(
                itemData,
                Game.currentLevel.getRandomTile(LevelElement.FLOOR).getCoordinate().toPoint());
    }

    /**
     * Creates a new Chest which drops the given items on interaction
     *
     * @param itemData which the chest is supposed to drop
     * @param position the position where the chest is placed
     */
    public Chest(List<ItemData> itemData, Point position) {
        new PositionComponent(this, position);
        InventoryComponent ic = new InventoryComponent(this, itemData.size());
        itemData.forEach(ic::addItem);
        new InteractionComponent(this, defaultInteractionRadius, false, this::openChest);
        AnimationComponent ac =
                new AnimationComponent(
                        this,
                        new Animation(DEFAULT_CLOSED_ANIMATION_FRAMES, 100, false),
                        new Animation(DEFAULT_OPENING_ANIMATION_FRAMES, 100, false));
    }
    private void openChest(Entity entity) {
        InventoryComponent heroInventoryC =
            (InventoryComponent)
                Game.getHero().get().getComponent(InventoryComponent.class).orElseThrow();


        ItemData key = null;
        for(ItemData item : heroInventoryC.getItems()){
            if(item instanceof ChestKey){
                key = item;
                break;
            }
        }

        if (key != null) {
            dropItems(entity);
            heroInventoryC.removeItem(key);
        } else {
            System.out.println("key was not found");
            Game.togglePause();
            LockPickingGame.playLockPickingGameAndWait(this);
            Game.togglePause();
        }
    }



    public void dropItems(Entity hero) {
        InventoryComponent heroInventoryC =
            hero.getComponent(InventoryComponent.class)
                .map(InventoryComponent.class::cast)
                .orElseThrow(
                    () ->
                        createMissingComponentException(
                            InventoryComponent.class.getName(), hero));

        ItemData key = null;
        for(ItemData item : heroInventoryC.getItems()){
            if(item instanceof ChestKey){
                key = item;
                break;
            }
        }

        heroInventoryC.removeItem(key);
        PositionComponent chestPositionC =
            this.getComponent(PositionComponent.class)
                .map(PositionComponent.class::cast)
                .orElseThrow(
                    () ->
                        createMissingComponentException(
                            PositionComponent.class.getName(), this));

        InventoryComponent chestInventoryC =
            this.getComponent(InventoryComponent.class)
                .map(InventoryComponent.class::cast)
                .orElseThrow(
                    () ->
                        createMissingComponentException(
                            InventoryComponent.class.getName(), this));

        List<ItemData> itemData = chestInventoryC.getItems();
        double count = itemData.size();
        IntStream.range(0, itemData.size())
            .forEach(
                index ->
                    itemData.get(index)
                        .triggerDrop(
                            this,
                            calculateDropPosition(chestPositionC, index / count)));

        this.getComponent(AnimationComponent.class)
            .map(AnimationComponent.class::cast)
            .ifPresent(x -> x.setCurrentAnimation(x.getIdleRight()));
    }



    /**
     * small Helper to determine the Position of the dropped item simple circle drop
     *
     * @param positionComponent The PositionComponent of the Chest
     * @param radian of the current Item
     * @return a Point in a unit Vector around the Chest
     */
    private static Point calculateDropPosition(PositionComponent positionComponent, double radian) {
        return new Point(
                (float) Math.cos(radian * Math.PI) + positionComponent.getPosition().x,
                (float) Math.sin(radian * Math.PI) + positionComponent.getPosition().y);
    }

    /**
     * Helper to create a MissingComponentException with a bit more information
     *
     * @param Component the name of the Component which is missing
     * @param e the Entity which did miss the Component
     * @return the newly created Exception
     */
    private static MissingComponentException createMissingComponentException(
            String Component, Entity e) {
        return new MissingComponentException(
                Component
                        + " missing in "
                        + Chest.class.getName()
                        + " in Entity "
                        + e.getClass().getName());
    }
}

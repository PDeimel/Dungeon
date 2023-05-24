package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.items.IOnCollect;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;

import java.util.List;

/**
 * An individual item which is supposed to expand the hero's inventory when collected
 */
public class Bag extends ItemData implements IOnCollect {

    private final static int SLOTS = 4;

    public Bag() {
        super(
            ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/bag/bag_inventory.png"),
            AnimationBuilder.buildAnimation("objects/items/bag/bag_world.png"),
            "Bag",
            "A shabby old bag that can carry a few items."
        );
    }

    /**
     * Once a bag has been collected, the items of the hero will be copied into a list,
     * a bigger inventory will be created for the hero and all of his current items are
     * copied into that, giving him more space.
     *
     * @param WorldItemEntity The bag on the ground
     * @param whoCollides Entity that picks up the item
     */
    @Override
    public void onCollect(Entity WorldItemEntity, Entity whoCollides) {
        whoCollides.getComponent(InventoryComponent.class).ifPresent(ic -> {
            List<ItemData> currItems = ((InventoryComponent) ic).getItems();
            int currSlots = currItems.size();
            if(whoCollides.getClass() == Hero.class) {
                whoCollides.removeComponent(InventoryComponent.class);
                whoCollides.addComponent(new InventoryComponent(whoCollides, currSlots + SLOTS));
                for (ItemData currItem : currItems) {
                    ((InventoryComponent) ic).addItem(currItem);
                }
            }
            });
    }
}

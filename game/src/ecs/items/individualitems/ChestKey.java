package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.items.ItemData;
import ecs.items.ItemType;

/** An item which is used to open a chest once */
public class ChestKey extends ItemData {

    public ChestKey() {
        super(
                ItemType.Active,
                AnimationBuilder.buildAnimation("objects/items/ChestKey/ChestKey_image.png"),
                AnimationBuilder.buildAnimation("objects/items/ChestKey/ChestKey_image.png"),
                "Chestkey",
                "A key that can open a treasure chest.");
    }
}

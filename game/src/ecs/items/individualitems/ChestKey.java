package ecs.items.individualitems;

import dslToGame.AnimationBuilder;
import ecs.components.HitboxComponent;
import ecs.components.InventoryComponent;
import ecs.entities.Entity;
import ecs.items.IOnCollect;
import ecs.items.IOnUse;
import ecs.items.ItemData;
import ecs.items.ItemType;

public class ChestKey extends ItemData {


    public ChestKey(){
        super(ItemType.Active,
            AnimationBuilder.buildAnimation("objects/items/ChestKey/ChestKey_image.png"),
            AnimationBuilder.buildAnimation("objects/items/ChestKey/ChestKey_image.png"),
            "Chestkey",
            "A key that can open a treasure chest.");


    }
}

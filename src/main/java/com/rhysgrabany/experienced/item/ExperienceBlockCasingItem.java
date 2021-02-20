package com.rhysgrabany.experienced.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ExperienceBlockCasingItem extends Item {

    public ExperienceBlockCasingItem() {
        super(new Properties()
                .maxStackSize(64)
                .group(ItemGroup.MISC));
    }

}

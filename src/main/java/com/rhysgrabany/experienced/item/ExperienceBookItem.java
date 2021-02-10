package com.rhysgrabany.experienced.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ExperienceBookItem extends Item {

    public ExperienceBookItem() {
        super(new Properties().maxStackSize(1));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

}

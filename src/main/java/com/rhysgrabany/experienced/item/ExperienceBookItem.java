package com.rhysgrabany.experienced.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ExperienceBookItem extends Item {

    public static final int MAX_EXP = 315;

    public ExperienceBookItem() {
        super(new Properties()
                .maxStackSize(1)
                .group(ItemGroup.MISC));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1; // TODO:1 = drained, 0 = full; lmao what try and find a way to do this right
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);

        int expTotal = expForLevel(playerIn.experienceLevel);


        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    private static int expForLevel(int level){
        if(level == 0) return 0;
        else if(level <= 16) return ((level*level) + 6 * level);
        else if(level <= 31) return (int)(2.5 * (level*level) - 40.5 * level + 360);
        return (int)(4.5 * (level*level) - 162.5 * level + 2220);
    }
    
}

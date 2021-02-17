package com.rhysgrabany.experienced.item;

import com.rhysgrabany.experienced.ModItems;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import com.rhysgrabany.experienced.util.ExperienceHelper;

import java.util.ArrayList;

public class ExperienceBookItem extends Item {

    public static final int MAX_EXP = 315;
    private static int currentStoredExp;

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
        return 1 - (currentStoredExp / MAX_EXP); // TODO:1 = drained, 0 = full; lmao what try and find a way to do this right
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(Hand.MAIN_HAND).getStack();

        // Get the stored exp from the book, or create the tag when it doesnt exist
        currentStoredExp = stack.getOrCreateTag().getInt("exp");
        int expLevel = playerIn.experienceTotal;

        // Giving exp to the book
        if ((!playerIn.isSneaking()) && handIn == Hand.MAIN_HAND && currentStoredExp < MAX_EXP) {

            int expToStore = (expLevel < (MAX_EXP - currentStoredExp)) ? expLevel : (MAX_EXP - currentStoredExp);

            playerIn.giveExperiencePoints(-expToStore);

            expToStore += currentStoredExp;
            stack.getTag().putInt("exp", expToStore);

            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        // Taking exp from the book
        if(playerIn.isSneaking() && handIn == Hand.MAIN_HAND && currentStoredExp > 0){
            int expToTake = ExperienceHelper.recieveExpToNextLevel(playerIn.experienceLevel);

            // This is mostly for the dregs in the book that needs to be removed
            if(expToTake > currentStoredExp && currentStoredExp > 0){
                expToTake = currentStoredExp;
            }

            // Give the exp to the player and update the amount in the book
            playerIn.giveExperiencePoints(expToTake);

            int expStored = currentStoredExp - expToTake;
            stack.getTag().putInt("exp", expStored);

            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        return new ActionResult<>(ActionResultType.PASS, stack);
    }


}

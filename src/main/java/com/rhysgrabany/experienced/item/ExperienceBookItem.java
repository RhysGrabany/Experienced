package com.rhysgrabany.experienced.item;

import com.rhysgrabany.experienced.ModItems;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
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
        return 1 / (currentStoredExp + 1); // TODO:1 = drained, 0 = full; lmao what try and find a way to do this right
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(Hand.MAIN_HAND).getStack();

        currentStoredExp = stack.getOrCreateTag().getInt("exp");



        if((!playerIn.isSneaking()) && handIn == Hand.MAIN_HAND && currentStoredExp < MAX_EXP){
            int expTotal = ExperienceHelper.playerTotalExp(playerIn);

            int expToStore = (expTotal < (MAX_EXP - currentStoredExp)) ? expTotal : (MAX_EXP - currentStoredExp);

            //TODO: not the way to remove exp from the player app
            playerIn.experienceTotal -= expToStore;

            expToStore += currentStoredExp;
            stack.getTag().putInt("exp", expToStore);

            return new ActionResult<>(ActionResultType.SUCCESS, stack);




        }



        return new ActionResult<>(ActionResultType.PASS, stack);
    }



}

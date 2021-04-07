package com.rhysgrabany.experienced.item;

import com.rhysgrabany.experienced.ModItems;
import com.rhysgrabany.experienced.capabilities.ModCapabilities;
import com.rhysgrabany.experienced.capabilities.experience.CapabilityProviderExpItems;
import com.rhysgrabany.experienced.capabilities.experience.ExperienceStorageHandler;
import com.rhysgrabany.experienced.capabilities.experience.ExperienceStorageProvider;
import com.rhysgrabany.experienced.capabilities.experience.IExperienceStorage;
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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;

// This is the default ExperienceBook class
public class ExperienceBookItem extends Item {

    public static final int MAX_EXP = 315;
//    private static int currentStoredExp;


    public ExperienceBookItem() {
        super(new Properties()
                .maxStackSize(1)
                .group(ItemGroup.MISC));
    }

    // This is needed to actually show the bar
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapabilityProviderExpItems(MAX_EXP);
    }

    // This is used for the progress bar for how much exp is in the Book
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        IExperienceStorage cap = stack.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);
        int currentStoredExp = cap.getExperienceStored();

        return 1D - ((double) currentStoredExp / (double) MAX_EXP); // TODO: Kinda works now but not really
    }



    // What happens when you right click while using the item
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(Hand.MAIN_HAND).getStack();
        IExperienceStorage cap = playerIn.getHeldItem(Hand.MAIN_HAND).getStack().getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);

        // Get the stored exp from the book, or create the tag when it doesnt exist
//        currentStoredExp = stack.getOrCreateTag().getInt("exp");
        int expTotal = playerIn.experienceTotal;
        int expLevel = playerIn.experienceLevel;

        // Giving exp to the book while the player is standing upright
        // TODO: Change this from ALL EXP AT ONCE to something like a level at time
        if ((!playerIn.isSneaking()) && handIn == Hand.MAIN_HAND) {

            int expToStoreToPrevLevel = ExperienceHelper.takeExpToPrevLevel(expLevel, expTotal);

            // Get all exp if it can fit, otherwise just put as much as you can
            // TODO: This might not work so check it out for >15 exp level
//            int expToStore = (expTotal < (MAX_EXP - currentStoredExp)) ? expTotal : (MAX_EXP - currentStoredExp);

            cap.receiveExperience(expToStoreToPrevLevel, false);

            // Take the exp away from the player, this updates the exp bar aswell
            playerIn.giveExperiencePoints(-expToStoreToPrevLevel);
//            ExperienceHelper.givePlayerExpAmount(-expToStoreToPrevLevel);

//            expToStore += currentStoredExp;
//            stack.getTag().putInt("exp", expToStore);

            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        // Taking exp from the book when the player is sneaking, and there is exp in the book
        if(playerIn.isSneaking() && handIn == Hand.MAIN_HAND){
            int expToTakeToNextLevel = ExperienceHelper.recieveExpToNextLevel(playerIn.experienceLevel);

            // This is mostly for the dregs in the book that needs to be removed
//            if(expToTakeToNextLevel > currentStoredExp && currentStoredExp > 0){
//                expToTakeToNextLevel = currentStoredExp;
//            }

            cap.extractExperience(expToTakeToNextLevel, false);

            // Give the exp to the player and update the amount in the book
            playerIn.giveExperiencePoints(expToTakeToNextLevel);
//            ExperienceHelper.givePlayerExpAmount(expToTakeToNextLevel);

//            int expStored = currentStoredExp - expToTakeToNextLevel;
//            stack.getTag().putInt("exp", expStored);

            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }

        // If the previous two statements dont work then just pass the result
        return new ActionResult<>(ActionResultType.PASS, stack);
    }


}

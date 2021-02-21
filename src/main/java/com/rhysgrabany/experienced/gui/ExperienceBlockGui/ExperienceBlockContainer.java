package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import com.rhysgrabany.experienced.ModContainers;
import com.rhysgrabany.experienced.gui.BaseContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;

public class ExperienceBlockContainer extends BaseContainer {

    private ExperienceBlockContainer(int windowId, PlayerInventory playerIn) {
        super(ModContainers.EXPERIENCE_BLOCK_CONTAINER.get(), windowId);
    }

    public static ExperienceBlockContainer createContainerServerSide(int windowId, PlayerInventory playerInventory){
        return new ExperienceBlockContainer(windowId, playerInventory);
    }

    public static ExperienceBlockContainer createContainerClientSide(int windowId, PlayerInventory playerInventory, PacketBuffer extraData){
//        ChestContents chestContents = ChestContents.createForClientSideContainer(ModChestTileEntity.NUMBER_OF_SLOTS);
        return new ExperienceBlockContainer(windowId, playerInventory);
    }



}

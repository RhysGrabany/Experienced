package com.rhysgrabany.experienced.gui;

import com.rhysgrabany.experienced.tile.BaseTile;
import com.sun.xml.internal.rngom.parse.host.Base;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public class BaseContainer extends Container {

    protected BaseContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }

}

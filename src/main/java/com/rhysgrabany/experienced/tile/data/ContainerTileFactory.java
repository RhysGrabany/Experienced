package com.rhysgrabany.experienced.tile.data;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.IContainerFactory;
import sun.jvm.hotspot.opto.Block;

public class ContainerTileFactory<C extends Container, T extends TileEntity> implements IContainerFactory<C> {

    public interface Factory<C, T>{
        C create(int windowId, PlayerInventory playerIn, T tile);
    }

    private final Factory<C, T> factory;

    public ContainerTileFactory(Factory<C, T> factory){
        this.factory = factory;
    }


    @Override
    public C create(int windowId, PlayerInventory inv, PacketBuffer data) {

        BlockPos pos = data.readBlockPos();

        T tile = (T) inv.player.world.getTileEntity(pos);

        return factory.create(windowId, inv, tile);



    }




}

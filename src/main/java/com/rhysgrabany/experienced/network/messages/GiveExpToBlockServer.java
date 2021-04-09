package com.rhysgrabany.experienced.network.messages;

import com.rhysgrabany.experienced.capabilities.ModCapabilities;
import com.rhysgrabany.experienced.capabilities.experience.IExperienceStorage;
import com.rhysgrabany.experienced.tile.BaseTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GiveExpToBlockServer {

    private int expAmount;
    private BlockPos blockPos;

    public GiveExpToBlockServer(int expAmount, BlockPos blockPos){
        this.expAmount = expAmount;
        this.blockPos = blockPos;
    }

    public static void encode(GiveExpToBlockServer msg, PacketBuffer buff){
        buff.writeInt(msg.expAmount);
        buff.writeBlockPos(msg.blockPos);
    }

    public static GiveExpToBlockServer decode(PacketBuffer buff){
        return new GiveExpToBlockServer(buff.readInt(), buff.readBlockPos());
    }

    public static void handle(GiveExpToBlockServer msg, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();
        PlayerEntity player = ctx.getSender();

        World world = player.world;

        TileEntity tile = world.getTileEntity(msg.blockPos);

        IExperienceStorage cap = tile.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);

        ctx.enqueueWork(() ->{

            cap.setExperience(msg.expAmount);
            tile.markDirty();

        });

        ctx.setPacketHandled(true);

    }




}

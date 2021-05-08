package com.rhysgrabany.experienced.network.messages;

import com.rhysgrabany.experienced.tile.BaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateContainerBatchMessage {


    private final BlockPos pos;
    private final CompoundNBT nbt;

    public UpdateContainerBatchMessage(BaseTile tile){
        this(tile.getPos(), tile.getUpdateTag());
    }

    private UpdateContainerBatchMessage(BlockPos pos, CompoundNBT nbt){
        this.pos = pos;
        this.nbt = nbt;
    }

    public static void encode(UpdateContainerBatchMessage msg, PacketBuffer buff){
        buff.writeBlockPos(msg.pos);
        buff.writeCompoundTag(msg.nbt);
    }

    public static UpdateContainerBatchMessage decode(UpdateContainerBatchMessage msg, PacketBuffer buff){
        return new UpdateContainerBatchMessage(buff.readBlockPos(), buff.readCompoundTag());
    }

    public static void handle(UpdateContainerBatchMessage msg, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {

            ClientWorld world = Minecraft.getInstance().world;

            if(world != null){
                BaseTile tile = (BaseTile)world.getTileEntity(msg.pos);

                if(tile == null){
                    return;
                } else {
                    tile.handleUpdatePacket(msg.nbt);
                }

            }

        });

        ctx.setPacketHandled(true);
    }



}

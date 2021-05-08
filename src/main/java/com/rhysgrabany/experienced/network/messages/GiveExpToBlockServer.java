package com.rhysgrabany.experienced.network.messages;

import com.rhysgrabany.experienced.capabilities.ModCapabilities;
import com.rhysgrabany.experienced.capabilities.experience.IExperienceStorage;
import com.rhysgrabany.experienced.tile.BaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GiveExpToBlockServer {

    private final BlockPos pos;
    private final CompoundNBT nbt;

    public GiveExpToBlockServer(BaseTile tile){
        this(tile.getPos(), tile.getUpdateTag());
    }

    public GiveExpToBlockServer(BlockPos blockPos, CompoundNBT nbt){
        this.pos = blockPos;
        this.nbt = nbt;
    }

    public static void encode(GiveExpToBlockServer msg, PacketBuffer buff){
        buff.writeBlockPos(msg.pos);
        buff.writeCompoundTag(msg.nbt);
    }

    public static GiveExpToBlockServer decode(PacketBuffer buff){
        return new GiveExpToBlockServer(buff.readBlockPos(), buff.readCompoundTag());
    }

    public static void handle(GiveExpToBlockServer msg, Supplier<NetworkEvent.Context> ctxSupplier){
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

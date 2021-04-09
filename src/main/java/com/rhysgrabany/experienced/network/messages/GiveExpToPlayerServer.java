package com.rhysgrabany.experienced.network.messages;

import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GiveExpToPlayerServer {
    private int expAmount;

    public GiveExpToPlayerServer(int expAmount){
        this.expAmount = expAmount;
    }


    public static void encocde(GiveExpToPlayerServer msg, PacketBuffer buff){
        buff.writeInt(msg.expAmount);

    }

    public static GiveExpToPlayerServer decode(PacketBuffer buff){
        return new GiveExpToPlayerServer(buff.readInt());
    }

    public static void handle(GiveExpToPlayerServer msg, Supplier<NetworkEvent.Context> ctxSupplier){

        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();


            // Check if player exists, and also if the ExperienceBlockContainer is acc open to combat cheating
            if(player == null || !(player.openContainer instanceof ExperienceBlockContainer)){
                return;
            }

            player.giveExperiencePoints(msg.expAmount);



        });
        ctx.setPacketHandled(true);

    }


}

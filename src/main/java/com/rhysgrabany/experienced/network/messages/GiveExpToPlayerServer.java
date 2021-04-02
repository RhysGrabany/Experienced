package com.rhysgrabany.experienced.network.messages;

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

    }


}

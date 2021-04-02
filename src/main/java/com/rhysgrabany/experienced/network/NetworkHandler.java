package com.rhysgrabany.experienced.network;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.network.messages.GiveExpToPlayerServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String VERSION = "1.0";

    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID, "network"),
            () -> VERSION, it -> it.equals(VERSION), it -> it.equals(VERSION));

    public static void init(){
        channel.registerMessage(0, GiveExpToPlayerServer.class, GiveExpToPlayerServer::encocde, GiveExpToPlayerServer::decode, GiveExpToPlayerServer::handle);
    }

}

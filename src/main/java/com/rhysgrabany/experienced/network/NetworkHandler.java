package com.rhysgrabany.experienced.network;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.network.messages.GiveExpToBlockServer;
import com.rhysgrabany.experienced.network.messages.GiveExpToPlayerServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String VERSION = "1.0";

    public static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID, "network"),
            () -> VERSION, it -> it.equals(VERSION), it -> it.equals(VERSION));

    public static void init(){
        int id = 0;
        channel.registerMessage(id, GiveExpToPlayerServer.class, GiveExpToPlayerServer::encocde, GiveExpToPlayerServer::decode, GiveExpToPlayerServer::handle);
        channel.registerMessage(id++, GiveExpToBlockServer.class, GiveExpToBlockServer::encode, GiveExpToBlockServer::decode, GiveExpToBlockServer::handle);
    }

    public static void sendTo(Object msg, PlayerEntity player){
        channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), msg);
    }

    public static void sendToServer(Object msg){
        channel.sendToServer(msg);
    }

}

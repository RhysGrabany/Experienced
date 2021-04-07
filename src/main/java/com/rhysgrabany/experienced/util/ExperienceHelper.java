package com.rhysgrabany.experienced.util;

import com.rhysgrabany.experienced.network.NetworkHandler;
import com.rhysgrabany.experienced.network.messages.GiveExpToPlayerServer;
import net.minecraft.entity.player.PlayerEntity;


// Basic static class that holds the methods for the items and blocks to use pertaining experience
public class ExperienceHelper {


    public static int playerTotalExp(PlayerEntity playerIn){
        return expForLevel(playerIn.experienceLevel) + expInBar(playerIn);
    }

    public static int expInBar(PlayerEntity playerIn){
        return (int)(playerIn.xpBarCap() * playerIn.experience);
    }

    // Method that will return the exp needed to get to that level, so how much TOTAL EXP needed to reach a LEVEL
    private static int expForLevel(int level){
        if(level == 0) return 0;
        else if(level <= 16) return ((level*level) + 6 * level);
        else if(level <= 31) return (int)(2.5 * (level*level) - 40.5 * level + 360);
        return (int)(4.5 * (level*level) - 162.5 * level + 2220);
    }

    // Method that will return the exp needed to level from the current level to the next level, such as 1 -> 2
    public static int recieveExpToNextLevel(int level){
        if(level >= 0 && level < 16) return 2 * level + 7;
        else if(level >= 16 && level < 31) return 5 * level - 38;
        return 9 * level - 158;
    }

    // Method used to get the experience needed to go back a level, easy to understand
    public static int takeExpToPrevLevel(int level, int total){
        if(total < 7){
            return total;
        }
        return recieveExpToNextLevel(level-1);
    }

    // Method that uses NetworkHandler to give the player an amount of exp
    public static void givePlayerExpAmount(int value){
        GiveExpToPlayerServer giveExp = new GiveExpToPlayerServer(value);
        NetworkHandler.channel.sendToServer(giveExp);
    }


}

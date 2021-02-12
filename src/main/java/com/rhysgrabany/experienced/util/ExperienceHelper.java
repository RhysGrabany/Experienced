package com.rhysgrabany.experienced.util;

import net.minecraft.entity.player.PlayerEntity;

public class ExperienceHelper {


    public static int playerTotalExp(PlayerEntity playerIn){
        return expForLevel(playerIn.experienceLevel) + expInBar(playerIn);
    }

    public static int expInBar(PlayerEntity playerIn){
        return (int)(playerIn.xpBarCap() * playerIn.experience);
    }

    private static int expForLevel(int level){
        if(level == 0) return 0;
        else if(level <= 16) return ((level*level) + 6 * level);
        else if(level <= 31) return (int)(2.5 * (level*level) - 40.5 * level + 360);
        return (int)(4.5 * (level*level) - 162.5 * level + 2220);
    }


}

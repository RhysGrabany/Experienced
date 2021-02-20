package com.rhysgrabany.experienced.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ExperienceBlock extends Block {

    // The tiers of the experience block, this makes it easy to register the blocks and all that sort of stuff
    public static Tier BLOCK_TIER;

    // Max Exp a block can hold
    private final int MAX_EXP;

    public enum Tier {
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        CREATIVE("creative");

        final String name;

        Tier(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }


    public ExperienceBlock(Tier tier) {
        super(AbstractBlock.Properties.create(Material.ROCK)
                .harvestLevel(2));
        BLOCK_TIER = tier;
        MAX_EXP = initMaxValue(tier);



    }

























    // Method used to initialize the max amount of exp a certain block can hold
    // For now; Small = 30, Medium = 60, Large = 100, Creative = MAX_VALUE, and default is 0 cause that might not happen
    private static int initMaxValue(Tier tier){
        switch(tier){
            case SMALL:
                return 1395;
            case MEDIUM:
                return 8670;
            case LARGE:
                return 30970;
            case CREATIVE:
                return Integer.MAX_VALUE;
            default:
                return 0;
        }
    }

}

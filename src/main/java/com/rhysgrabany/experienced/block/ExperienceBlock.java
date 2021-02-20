package com.rhysgrabany.experienced.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ExperienceBlock extends Block {

    // The tiers of the experience block, this makes it easy to register the blocks and all that sort of stuff
    public static Tier BLOCK_TIER;

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
    }

}

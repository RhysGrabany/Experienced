package com.rhysgrabany.experienced.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ExperienceBlock extends Block {

    public enum TIER {
        SMALL,
        MEDIUM,
        LARGE,
        CREATIVE
    }


    public ExperienceBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK)
                .harvestLevel(0));
    }

}

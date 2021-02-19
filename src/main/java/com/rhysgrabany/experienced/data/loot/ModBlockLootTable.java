package com.rhysgrabany.experienced.data.loot;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

public class ModBlockLootTable extends BlockLootTables {

    @Override
    protected void addTables() {

        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            registerDropSelfLootTable(ModBlocks.EXPERIENCE_BLOCKS.get(tier).get());
        }
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> Constants.MOD_ID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}

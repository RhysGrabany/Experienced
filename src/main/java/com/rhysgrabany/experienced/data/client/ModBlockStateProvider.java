package com.rhysgrabany.experienced.data.client;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlock(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.SMALL).get());
        simpleBlock(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.MEDIUM).get());
        simpleBlock(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.LARGE).get());
        simpleBlock(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.LARGE).get());

    }



}

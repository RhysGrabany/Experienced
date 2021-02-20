package com.rhysgrabany.experienced.data.client;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

// Data Gen for the mod block models
public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // Loop through the ExpBlocks and make them a simple block (no directional property needed cause all faces have the same texture)
        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            simpleBlock(ModBlocks.EXPERIENCE_BLOCKS.get(tier).get());
        }
    }



}

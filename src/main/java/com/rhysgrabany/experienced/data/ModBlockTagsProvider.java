package com.rhysgrabany.experienced.data;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {

        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            getOrCreateBuilder(ModTags.Blocks.EXPERIENCE_BLOCK)
                    .add(ModBlocks.EXPERIENCE_BLOCKS.get(tier).get());
        }


    }
}

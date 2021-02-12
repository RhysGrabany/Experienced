package com.rhysgrabany.experienced.data;

import com.rhysgrabany.experienced.ModItems;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Constants.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerTags() {

        //TODO: Find better Tag
        getOrCreateBuilder(ModTags.Items.EXPERIENCE_BOOK)
                .add(ModItems.EXPERIENCE_BOOK.get());
        getOrCreateBuilder(Tags.Items.STRING);
    }
}

package com.rhysgrabany.experienced.data.client;

import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

// Provider for the Items in Data Gen
public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // Since the blocks already have their textures made and all, it'd be a waste to do it again
        // This basically points to the block and say "just like that"
        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            withExistingParent("experience_block_" + tier.getName(), modLoc("block/experience_block_" + tier.getName()));
        }

        // The location of the model file, this is used when all parts have been generated
        ModelFile itemGen = getExistingFile(mcLoc("item/generated"));

        builder(itemGen, "experience_book");
    }

    // This is used to point to a texture for an item and links it
    private ItemModelBuilder builder(ModelFile itemGen, String name){
        return getBuilder(name).parent(itemGen).texture("layer0", "item/" + name);
    }
}

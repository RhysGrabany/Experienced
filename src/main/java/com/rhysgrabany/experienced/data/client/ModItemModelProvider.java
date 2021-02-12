package com.rhysgrabany.experienced.data.client;

import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile itemGen = getExistingFile(mcLoc("item/generated"));

        builder(itemGen, "experience_book");


    }

    private ItemModelBuilder builder(ModelFile itemGen, String name){
        return getBuilder(name).parent(itemGen).texture("layer0", "item/" + name);
    }
}

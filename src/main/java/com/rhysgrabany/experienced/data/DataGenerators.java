package com.rhysgrabany.experienced.data;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.data.client.ModBlockStateProvider;
import com.rhysgrabany.experienced.data.client.ModItemModelProvider;
import com.rhysgrabany.experienced.data.loot.ModLootTables;
import com.rhysgrabany.experienced.data.recipe.ModRecipesProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){

        DataGenerator generator = event.getGenerator();
        ExistingFileHelper eFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(generator, eFileHelper);

//        generator.addProvider(new ModBlockTagsProvider(generator, eFileHelper));
        generator.addProvider(new ModItemTagsProvider(generator, blockTags, eFileHelper));

        generator.addProvider(new ModLootTables(generator));
        generator.addProvider(new ModRecipesProvider(generator));

        generator.addProvider(new ModBlockStateProvider(generator, eFileHelper));
        generator.addProvider(new ModItemModelProvider(generator, eFileHelper));



    }


}

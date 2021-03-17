package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.ModContainers;
import com.rhysgrabany.experienced.ModItems;
import com.rhysgrabany.experienced.ModTiles;

// This is the class that deals with the registration of different parts of the mod
// Blocks, Items, Recipes, Tiles, etc etc
public class Registration {

    public static void register(){

        // Registry objects are registered
        ModItems.register();
        ModBlocks.register();
        ModRecipeSerializers.register();
        ModTiles.register();
        ModContainers.register();


    }

}

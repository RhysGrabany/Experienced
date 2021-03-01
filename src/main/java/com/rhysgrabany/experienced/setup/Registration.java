package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.ModContainers;
import com.rhysgrabany.experienced.ModItems;
import com.rhysgrabany.experienced.ModTiles;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.item.ExperienceBookItem;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// This is the class that deals with the registration of different parts of the mod
// Blocks, Items, Recipes, Tiles, etc etc
public class Registration {

    public static void register(){

        // Registry objects are registered
        ModItems.register();
        ModBlocks.register();
        ModRecipes.register();
        ModTiles.register();
        ModContainers.register();


    }

}

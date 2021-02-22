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

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);




    public static void register(){

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // The deferred registries are registered before the registry objects are registered
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        RECIPES.register(modEventBus);
        TILES.register(modEventBus);

        // Registry objects are registered
        ModItems.register();
        ModBlocks.register();
        ModRecipes.register();
        ModTiles.register();
        ModContainers.register();




    }


}

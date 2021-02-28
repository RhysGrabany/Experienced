package com.rhysgrabany.experienced;


import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.setup.Registration;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.EnumMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTiles {

    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);

    public static final RegistryObject<TileEntityType<ExperienceBlockTile>> EXPERIENCE_BLOCK_SMALL;
    public static final RegistryObject<TileEntityType<ExperienceBlockTile>> EXPERIENCE_BLOCK_MEDIUM;
    public static final RegistryObject<TileEntityType<ExperienceBlockTile>> EXPERIENCE_BLOCK_LARGE;
    public static final RegistryObject<TileEntityType<ExperienceBlockTile>> EXPERIENCE_BLOCK_CREATIVE;


    static {

        EXPERIENCE_BLOCK_SMALL = TILES.register("experience_block_small", ()-> TileEntityType.Builder.create(
                ()-> new ExperienceBlockTile(ExperienceBlock.Tier.SMALL), ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.SMALL).get()
        ).build(null));
        EXPERIENCE_BLOCK_MEDIUM = TILES.register("experience_block_medium", ()-> TileEntityType.Builder.create(
                ()-> new ExperienceBlockTile(ExperienceBlock.Tier.MEDIUM), ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.MEDIUM).get()
        ).build(null));
        EXPERIENCE_BLOCK_LARGE = TILES.register("experience_block_large", ()-> TileEntityType.Builder.create(
                ()-> new ExperienceBlockTile(ExperienceBlock.Tier.LARGE), ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.LARGE).get()
        ).build(null));
        EXPERIENCE_BLOCK_CREATIVE = TILES.register("experience_block_creative", ()-> TileEntityType.Builder.create(
                ()-> new ExperienceBlockTile(ExperienceBlock.Tier.CREATIVE), ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.CREATIVE).get()
        ).build(null));


    }

    public static void register() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}

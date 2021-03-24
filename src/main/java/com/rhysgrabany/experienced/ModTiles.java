package com.rhysgrabany.experienced;


import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTiles {

    private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);

    public static final Map<ExperienceBlock.Tier, RegistryObject<TileEntityType<ExperienceBlockTile>>> EXPERIENCE_BLOCK_TILES = new EnumMap<>(ExperienceBlock.Tier.class);


    static {

        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            EXPERIENCE_BLOCK_TILES.put(tier, TILES.register("experience_block_" + tier.getName(), ()-> TileEntityType.Builder.create(
                    ()-> new ExperienceBlockTile(tier), ModBlocks.EXPERIENCE_BLOCKS.get(tier).get()
            ).build(null)));
        }


    }

    public static void register() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}

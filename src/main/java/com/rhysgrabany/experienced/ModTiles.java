package com.rhysgrabany.experienced;


import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTiles {

    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);

    public static final TileEntityType<ExperienceBlockTile> EXPERIENCE_BLOCK_TILE;

    static{
        EXPERIENCE_BLOCK_TILE = TileEntityType.Builder.create(ExperienceBlockTile::new).build(null);
    }

//    @SubscribeEvent
//    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> e){
//        IForgeRegistry<TileEntityType<?>> r = e.getRegistry();
//
//        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
//            r.register(EXPERIENCE_BLOCK_TILE
//                    .setRegistryName(ModBlocks.EXPERIENCE_BLOCKS
//                            .get(tier)
//                            .getId()));
//        }
//    }

    public static void register(){
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }




}

package com.rhysgrabany.experienced;


import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModTiles {

    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Constants.MOD_ID);

    public static final TileEntityType<ExperienceBlockTile> EXPERIENCE_BLOCK_TILE;

    static{
        EXPERIENCE_BLOCK_TILE = TileEntityType.Builder.create(ExperienceBlockTile::new).build(null);
    }




}

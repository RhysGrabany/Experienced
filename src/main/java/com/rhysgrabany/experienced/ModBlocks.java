package com.rhysgrabany.experienced;

import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.Map;

//This is a simple class that just holds the registration of blocks in the mod
// Blocks gets registered when the class is called
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static final Map<ExperienceBlock.Tier, RegistryObject<Block>> EXPERIENCE_BLOCKS = new EnumMap<>(ExperienceBlock.Tier.class);


    static{

        // All the blocks for experience block gets registered to the Map so they can be referenced by tier easily
        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            EXPERIENCE_BLOCKS.put(tier, BLOCKS.register("experience_block_" + tier.getName(),
                    () -> new ExperienceBlock(ExperienceBlock.Tier.valueOf(tier.name()))));
        }

    }


    public static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private ModBlocks(){}


}

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

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static final Map<ExperienceBlock.Tier, RegistryObject<Block>> EXPERIENCE_BLOCKS = new EnumMap<>(ExperienceBlock.Tier.class);

//    public static final RegistryObject<Block> EXPERIENCE_BLOCK_SMALL;
//    public static final RegistryObject<Block> EXPERIENCE_BLOCK_MEDIUM;
//    public static final RegistryObject<Block> EXPERIENCE_BLOCK_LARGE;
//    public static final RegistryObject<Block> EXPERIENCE_BLOCK_CREATIVE;


    static{

        for(ExperienceBlock.Tier tier : ExperienceBlock.Tier.values()){
            EXPERIENCE_BLOCKS.put(tier, BLOCKS.register("experience_block_" + tier.getName(),
                    () -> new ExperienceBlock(ExperienceBlock.Tier.valueOf(tier.name()))));
        }


//        EXPERIENCE_BLOCK_SMALL = BLOCKS.register("experience_block_small", ()-> new ExperienceBlock(ExperienceBlock.Tier.SMALL));
//        EXPERIENCE_BLOCK_MEDIUM = BLOCKS.register("experience_block_medium", ()-> new ExperienceBlock(ExperienceBlock.Tier.MEDIUM));
//        EXPERIENCE_BLOCK_LARGE = BLOCKS.register("experience_block_large", ()-> new ExperienceBlock(ExperienceBlock.Tier.LARGE));
//        EXPERIENCE_BLOCK_CREATIVE = BLOCKS.register("experience_block_creative", ()-> new ExperienceBlock(ExperienceBlock.Tier.CREATIVE));
    }


    public static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private ModBlocks(){}


}

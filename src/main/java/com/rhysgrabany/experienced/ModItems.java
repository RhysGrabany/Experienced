package com.rhysgrabany.experienced;

import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.item.BaseBlockItem;
import com.rhysgrabany.experienced.item.ExperienceBookItem;
import com.rhysgrabany.experienced.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<Item> EXPERIENCE_BOOK;

    static{
        EXPERIENCE_BOOK = Registration.ITEMS.register("experience_book", ExperienceBookItem::new);


        for(RegistryObject<Block> block_tier : ModBlocks.EXPERIENCE_BLOCKS.values()){
            registerBlockItemFor(block_tier);

        }
    }

    public static void register(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    private static <T extends Block> RegistryObject<BlockItem> registerBlockItemFor(RegistryObject<T> block){
        return ITEMS.register(block.getId().getPath(), ()-> new BaseBlockItem(block.get(), new Item.Properties().group(ItemGroup.MISC)));
    }

    private ModItems(){}


}

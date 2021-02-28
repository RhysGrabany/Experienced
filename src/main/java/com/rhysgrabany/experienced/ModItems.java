package com.rhysgrabany.experienced;

import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.item.BaseBlockItem;
import com.rhysgrabany.experienced.item.ExperienceBlockCasingItem;
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

// Class that registers the items, and block items, of the mod
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {


    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<Item> EXPERIENCE_BOOK;
    public static final RegistryObject<Item> EXPERIENCE_BLOCK_CASING;

    static{
        EXPERIENCE_BOOK = ITEMS.register("experience_book", ExperienceBookItem::new);
        EXPERIENCE_BLOCK_CASING = ITEMS.register("experience_block_casing", ExperienceBlockCasingItem::new);

        // All the blocks need their item equivalent otherwise loot tables wont work
        // A for each loop is great when used with Tier
        for(RegistryObject<Block> block_tier : ModBlocks.EXPERIENCE_BLOCKS.values()){
            registerBlockItemFor(block_tier);

        }
    }

    public static void register(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Method used to register the BlockItem of each block, without this then there'd be no block drops
    private static <T extends Block> RegistryObject<BlockItem> registerBlockItemFor(RegistryObject<T> block){
        return ITEMS.register(block.getId().getPath(), ()-> new BaseBlockItem(block.get(), new Item.Properties().group(ItemGroup.MISC))); //TODO: Change from misc to something else
    }

    private ModItems(){}


}

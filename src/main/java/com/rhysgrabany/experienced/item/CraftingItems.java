package com.rhysgrabany.experienced.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.List;


//TODO: I dont know what this does again, this is what happens when you follow a tutorial and don't question stuff
public enum CraftingItems implements IItemProvider {

    EXPERIENCE_BOOK,
    EXPERIENCE_BLOCK_SMALL,
    EXPERIENCE_BLOCK_MEDIUM,
    EXPERIENCE_BLOCK_LARGE,
    ;

    private RegistryObject<ItemInternal> item = null;

    @Override
    public Item asItem() {

        if(this.item == null){
            throw new NullPointerException("Crafting Items accessed too early");
        }
        return this.item.get();
    }

    public static void register(DeferredRegister<Item> items){
        for(CraftingItems item : values()){
            item.item = items.register(item.name(), ItemInternal::new);
        }
    }



    private static final class ItemInternal extends Item{

        public ItemInternal() {
            super(new Properties().group(ItemGroup.MISC));
        }

        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
            String descKey = this.getTranslationKey() + ".desc";

            if(I18n.hasKey(descKey)){
                tooltip.add(new TranslationTextComponent(descKey));
            }


        }
    }


}

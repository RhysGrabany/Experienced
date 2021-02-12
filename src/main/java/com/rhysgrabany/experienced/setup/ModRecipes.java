package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModRecipes {

    private ModRecipes(){}

    static void register(){}

    private static RegistryObject<IRecipeSerializer<?>> register(String name, Supplier<IRecipeSerializer<?>> serializer){
        return register(Constants.MOD_ID.toString(), serializer);
    }

    private static RegistryObject<IRecipeSerializer<?>> register(ResourceLocation id, Supplier<IRecipeSerializer<?>> serializer){
        return Registration.RECIPES.register(id.getPath(), serializer);
    }


}

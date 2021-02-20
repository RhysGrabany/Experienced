package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

// ModRecipes allow all the recipes in the mod to be registered
public class ModRecipes {

    private ModRecipes(){}

    static void register(){}

    // The RegistryObject for Recipes are registered first, and then the DeferredRegister in Registration also get registered
    private static RegistryObject<IRecipeSerializer<?>> register(String name, Supplier<IRecipeSerializer<?>> serializer){
        return register(Constants.MOD_ID, serializer);
    }

    private static RegistryObject<IRecipeSerializer<?>> register(ResourceLocation id, Supplier<IRecipeSerializer<?>> serializer){
        return Registration.RECIPES.register(id.getPath(), serializer);
    }


}

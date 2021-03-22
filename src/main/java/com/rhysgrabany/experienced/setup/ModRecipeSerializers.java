package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.recipe.recipes.ExperienceBlockRecipe;
import com.rhysgrabany.experienced.recipe.impl.ExperienceBlockIRecipe;
import com.rhysgrabany.experienced.recipe.serializer.ExperienceBlockSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

// ModRecipeSerializers allow all the recipe in the mod to be registered
public class ModRecipeSerializers {



    private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<ExperienceBlockIRecipe>> EXPERIENCE_BLOCK_RECIPE;

    static{
        EXPERIENCE_BLOCK_RECIPE = RECIPES.register("experience_block_recipe",
                () -> new ExperienceBlockSerializer<>(ExperienceBlockIRecipe::new));
    }






    private ModRecipeSerializers(){}

    static void register(){}

    // The RegistryObject for Recipes are registered first, and then the DeferredRegister in Registration also get registered
    private static RegistryObject<IRecipeSerializer<?>> register(String name, Supplier<IRecipeSerializer<?>> serializer){
        return register(Constants.MOD_ID, serializer);
    }

    private static RegistryObject<IRecipeSerializer<?>> register(ResourceLocation id, Supplier<IRecipeSerializer<?>> serializer){
        return RECIPES.register(id.getPath(), serializer);
    }


}

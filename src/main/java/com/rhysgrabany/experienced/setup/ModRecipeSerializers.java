package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.recipe.impl.ExperienceBlockIRecipe;
import com.rhysgrabany.experienced.recipe.serializer.ExperienceBlockSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// ModRecipeSerializers allow all the recipe in the mod to be registered
public class ModRecipeSerializers {



    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<ExperienceBlockIRecipe>> EXPERIENCE_BLOCK_RECIPE;

    static{

        EXPERIENCE_BLOCK_RECIPE = RECIPE_SERIALIZERS.register("experience_block_recipe",
                () -> new ExperienceBlockSerializer<>(ExperienceBlockIRecipe::new));

    }


    private ModRecipeSerializers(){}

    public static void register(){
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}

package com.rhysgrabany.experienced.recipe.serializer;

import com.google.gson.JsonObject;
import com.rhysgrabany.experienced.recipe.ExperienceBlockRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import javax.annotation.Resource;

public class ExperienceBlockSerializer <R extends ExperienceBlockRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<R> {

    private final IFactory<R> factory;

    public ExperienceBlockSerializer(IFactory<R> factory){
        this.factory = factory;
    }


    @Override
    public R read(ResourceLocation recipeId, JsonObject json) {
        return null;
    }

    @Nullable
    @Override
    public R read(ResourceLocation recipeId, PacketBuffer buffer) {
        return null;
    }

    @Override
    public void write(PacketBuffer buffer, R recipe) {

    }

    @FunctionalInterface
    public interface IFactory<R extends ExperienceBlockRecipe>{

        R create(ResourceLocation id, ItemStack input, ItemStack output, int expAmount);

    }


}

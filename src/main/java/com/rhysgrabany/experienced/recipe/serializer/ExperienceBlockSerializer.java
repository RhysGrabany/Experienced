package com.rhysgrabany.experienced.recipe.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.recipe.impl.ExperienceBlockIRecipe;
import com.rhysgrabany.experienced.recipe.impl.ItemIngredient;
import com.rhysgrabany.experienced.recipe.recipes.ExperienceBlockRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ExperienceBlockSerializer <R extends ExperienceBlockRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>>
        implements IRecipeSerializer<R> {

    private final IFactory<R> factory;

    public ExperienceBlockSerializer(IFactory<R> factory){
        this.factory = factory;
    }


    @Override
    public R read(ResourceLocation recipeId, JsonObject json) {

        JsonElement jsonInput = JSONUtils.isJsonArray(json, Constants.Json.INPUT) ?
                JSONUtils.getJsonArray(json, Constants.Json.INPUT) : JSONUtils.getJsonObject(json, Constants.Json.INPUT);
        ItemIngredient input = ItemIngredient.deserialize(jsonInput);

        ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, Constants.Json.OUTPUT));

        int exp = JSONUtils.getInt(json, "exp");

        return this.factory.create(recipeId, input, output, exp);

    }

    @Nullable
    @Override
    public R read(ResourceLocation recipeId, PacketBuffer buffer) {

        ItemIngredient input = ItemIngredient.read(buffer);
        ItemStack output = buffer.readItemStack();
        int exp = buffer.readInt();

        return this.factory.create(recipeId, input, output, exp);

    }

    @Override
    public void write(PacketBuffer buffer, R recipe) {

        recipe.write(buffer);

    }

    @FunctionalInterface
    public interface IFactory<R extends ExperienceBlockRecipe>{

        R create(ResourceLocation id, ItemIngredient input, ItemStack output, int expAmount);

    }


}

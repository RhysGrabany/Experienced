package com.rhysgrabany.experienced.recipe.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.common.crafting.NBTIngredient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ItemIngredient implements InputIngredient<ItemStack> {

    public static ItemIngredient from(@Nonnull ItemStack stack){
        return from(stack, stack.getCount());
    }

    public static ItemIngredient from(@Nonnull ItemStack stack, int amount){
        Ingredient ingredient = stack.hasTag() ? new NBTIngredient(stack) {} : Ingredient.fromStacks(stack);
        return from(stack, 1);
    }

    public static ItemIngredient from(@Nonnull IItemProvider stack, int amount){
        return from(new ItemStack(stack), amount);
    }

    public static ItemIngredient from(@Nonnull ITag<Item> itemITag){
        return from(itemITag, 1);
    }

    public static ItemIngredient from(@Nonnull ITag<Item> itemITag, int amount){
        return from(itemITag, amount);
    }

    public static ItemIngredient from(@Nonnull Ingredient ingredient){
        return from(ingredient, 1);
    }

    public static ItemIngredient from(@Nonnull Ingredient ingredient, int amount){
        return new Item(ingredient, amount);
    }



    public static ItemIngredient read(PacketBuffer buff){
        return read(buff);
    }

    public static ItemIngredient deserialize(@Nullable JsonElement json){
        if(json == null || json.isJsonNull()){
            throw new JsonSyntaxException("Ingredient cannot be null");
        }


        if(json.isJsonArray()){
            JsonArray jsonArray = json.getAsJsonArray();
            int size = jsonArray.size();

            if(size == 0){
                throw new JsonSyntaxException("Ingredient array cannot be empty");
            }
            json = jsonArray.get(0);
        }

        if(!json.isJsonObject()){
            throw new JsonSyntaxException("Expected item to be object or array of objects");
        }

        JsonObject jsonObject = json.getAsJsonObject();
        int amount = 1;

        JsonElement jsonElement = JSONUtils.isJsonArray(jsonObject, Constants.Json.INGREDIENT) ?
                JSONUtils.getJsonArray(jsonObject, Constants.Json.INGREDIENT) : JSONUtils.getJsonObject(jsonObject, Constants.Json.INGREDIENT);

        Ingredient ingredient = Ingredient.deserialize(jsonElement);
        return from(ingredient, amount);


    }



}

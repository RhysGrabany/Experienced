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
import java.util.Objects;

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
        return new Single(ingredient, amount);
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

    public static class Single extends ItemIngredient{

        @Nonnull
        private final Ingredient ingredient;
        private final int amount;

        public Single(@Nonnull Ingredient ingredient, int amount){
            this.ingredient = Objects.requireNonNull(ingredient);
            this.amount = amount;
        }


        @Override
        public boolean test(ItemStack itemStack) {
            return testType(itemStack) && itemStack.getCount() >= amount;
        }

        @Override
        public boolean testType(@Nonnull ItemStack type) {
            return ingredient.test(type);
        }

        @Override
        public ItemStack getMatchingInstance(ItemStack type) {
            if(test(type)){
                ItemStack matching = type.copy();
                matching.setCount(amount);
                return matching;
            }
            return ItemStack.EMPTY;
        }

        @Override
        public long getNeededAmount(ItemStack type) {
            return testType(type) ? amount : 0;
        }

        @Override
        public void write(PacketBuffer buff) {
            buff.writeEnumValue(IngredientType.SINGLE);
            ingredient.write(buff);
            buff.writeVarInt(amount);
        }

        @Nonnull
        @Override
        public JsonElement serialize() {
            JsonObject json = new JsonObject();

            json.add("ingredient", ingredient.serialize());
            return json;
        }

        public static Single read(PacketBuffer buff){
            return new Single(Ingredient.read(buff), buff.readVarInt());
        }
    }

    private enum IngredientType{
        SINGLE;
    }






}

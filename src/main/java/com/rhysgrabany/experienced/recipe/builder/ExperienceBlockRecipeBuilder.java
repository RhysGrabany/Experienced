package com.rhysgrabany.experienced.recipe.builder;

import com.google.gson.JsonObject;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.recipe.impl.ItemIngredient;
import com.rhysgrabany.experienced.recipe.recipes.ExperienceBlockRecipe;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ExperienceBlockRecipeBuilder extends ExperiencedRecipeBuilder<ExperienceBlockRecipeBuilder>{

    private final ItemIngredient input;
    private final ItemIngredient output;
    private final int expAmount;

    protected ExperienceBlockRecipeBuilder(ItemIngredient input, ItemIngredient output, int expAmount){
        super(expSerializer("experience_block"));
        this.input = input;
        this.output = output;
        this.expAmount = expAmount;
    }

    public static ExperienceBlockRecipeBuilder experienced(ItemIngredient input, ItemIngredient output, int expAmount){
        if(output == null){
            throw new IllegalArgumentException("Experienced Recipe requires an output");
        }
        return new ExperienceBlockRecipeBuilder(input, output, expAmount);
    }


//    public void build(Consumer<IFinishedRecipe> consumer) {
//        build(consumer, output);
//    }

    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return null;
    }


    public class ExperienceBlockRecipeResult extends RecipeResult{
        protected ExperienceBlockRecipeResult(ResourceLocation id){
            super(id);
        }

        @Override
        public void serialize(JsonObject json) {
            json.add(Constants.Json.INPUT, input.serialize());
            json.addProperty(Constants.Json.EXP, expAmount);
            json.add(Constants.Json.OUTPUT, output.serialize());
        }
    }


}

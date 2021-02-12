package com.rhysgrabany.experienced.data.recipe;

import com.rhysgrabany.experienced.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.EXPERIENCE_BOOK.get(), 1)
                .addIngredient(Items.BOOK)
                .addIngredient(Items.EMERALD)
                .addIngredient(Items.ENDER_EYE)
                .addCriterion("has_item", hasItem(Items.EMERALD))
                .build(consumer);
    }
}

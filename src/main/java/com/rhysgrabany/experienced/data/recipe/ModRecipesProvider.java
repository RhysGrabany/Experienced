package com.rhysgrabany.experienced.data.recipe;

import com.rhysgrabany.experienced.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerItemRecipes(consumer);
        //registerBlockRecipes(consumer);
    }

    private void registerItemRecipes(Consumer<IFinishedRecipe> consumer){
        // Recipe for the Experience Book Item
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.EXPERIENCE_BOOK.get(), 1)
                .addIngredient(Items.BOOK)
                .addIngredient(Items.EMERALD)
                .addIngredient(Items.ENDER_EYE)
                .addCriterion("has_item", hasItem(Items.EMERALD))
                .build(consumer);

        //Recipe for the Experience Block Casing Item
        ShapedRecipeBuilder.shapedRecipe(ModItems.EXPERIENCE_BLOCK_CASING.get(), 3)
                .key('i', Items.IRON_INGOT)
                .key('g', Items.GLASS)
                .key('b', ModItems.EXPERIENCE_BOOK.get())
                .patternLine("igi")
                .patternLine("gbg")
                .patternLine("igi")
                .addCriterion("has_item", hasItem(ModItems.EXPERIENCE_BOOK.get()))
                .build(consumer);
    }


    private void registerBlockRecipes(Consumer<IFinishedRecipe> consumer){

    }





}

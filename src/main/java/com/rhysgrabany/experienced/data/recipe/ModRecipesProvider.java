package com.rhysgrabany.experienced.data.recipe;

import com.rhysgrabany.experienced.Experienced;
import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.ModItems;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.recipe.builder.ExperienceBlockRecipeBuilder;
import com.rhysgrabany.experienced.recipe.builder.ExperiencedRecipeBuilder;
import com.rhysgrabany.experienced.recipe.impl.ItemIngredient;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerItemRecipes(consumer);
        registerBlockRecipes(consumer);
    }

    // Register the recipe for the Items in the Mod
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


    // Register the recipe for the Blocks in the Mod
    private void registerBlockRecipes(Consumer<IFinishedRecipe> consumer){

        // Recipe for Small Experience Block
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.SMALL).get(), 1)
                .key('d', Items.BLUE_DYE)
                .key('c', ModItems.EXPERIENCE_BLOCK_CASING.get())
                .key('b', Items.LAPIS_BLOCK)
                .patternLine("dcd")
                .patternLine("cbc")
                .patternLine("dcd")
                .addCriterion("has_item", hasItem(ModItems.EXPERIENCE_BLOCK_CASING.get()))
                .build(consumer);

        ExperienceBlockRecipeBuilder.experienced(ItemIngredient.from(Ingredient.fromItems(ModItems.EXPERIENCE_BOOK.get().asItem()), 1),
                ItemIngredient.from(Ingredient.fromItems(ModItems.EXPERIENCE_BOOK.get().asItem()), 1), 10)
                .addCriterion("has_item", hasItem())
                .build(consumer, Experienced.getId(ModItems.EXPERIENCE_BOOK.get().getName() + "_exp"));

//        CookingRecipeBuilder.smeltingRecipe();


        // Recipe for Medium Experience Block
        // TODO: Find a suitable replacement for Redstone Block, I don't want to use emerald block that will mean they will need 18 emeralds just for the next tier making this too expensive overall
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.MEDIUM).get(), 1)
                .key('d', Items.GREEN_DYE)
                .key('c', ModItems.EXPERIENCE_BLOCK_CASING.get())
                .key('s', ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.SMALL).get())
                .key('b', Items.REDSTONE_BLOCK)
                .patternLine("dcd")
                .patternLine("sbs")
                .patternLine("dcd")
                .addCriterion("has_item", hasItem(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.SMALL).get()))
                .build(consumer);

        //Recipe for Large Experience Block
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.LARGE).get(), 1)
                .key('d', Items.PURPLE_DYE)
                .key('c', ModItems.EXPERIENCE_BLOCK_CASING.get())
                .key('m', ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.MEDIUM).get())
                .key('b', Items.PURPUR_BLOCK)
                .patternLine("dcd")
                .patternLine("mbm")
                .patternLine("dcd")
                .addCriterion("has_item", hasItem(ModBlocks.EXPERIENCE_BLOCKS.get(ExperienceBlock.Tier.MEDIUM).get()))
                .build(consumer);

    }





}

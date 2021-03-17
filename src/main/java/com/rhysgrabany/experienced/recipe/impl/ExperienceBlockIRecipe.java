package com.rhysgrabany.experienced.recipe.impl;

import com.rhysgrabany.experienced.ModBlocks;
import com.rhysgrabany.experienced.recipe.ExperiencedRecipeType;
import com.rhysgrabany.experienced.recipe.recipes.ExperienceBlockRecipe;
import com.rhysgrabany.experienced.setup.ModRecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public class ExperienceBlockIRecipe extends ExperienceBlockRecipe {

    //TODO: Will I really need this? Find out next time on DBZ
    public ExperienceBlockIRecipe(ResourceLocation id, ItemStack input, ItemStack output, int expAmount) {
        super(id, input, output, expAmount);
    }

    @Override
    public IRecipeType<ExperienceBlockRecipe> getType() {
        return ExperiencedRecipeType.EXPERIENCE_BLOCK;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EXPERIENCE_BLOCK_RECIPE.get();
    }

    @Override
    public String getGroup() {
        return ModBlocks.EXPERIENCE_BLOCKS.getClass().getName();
    }

}

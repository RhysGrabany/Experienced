package com.rhysgrabany.experienced.recipe.builder;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ExperienceBlockRecipeBuilder extends ExperiencedRecipeBuilder<ExperienceBlockRecipeBuilder>{

    private final ItemStack input;
    private final ItemStack output;
    private final int expAmount;

    protected ExperiencedBlockRecipeBuilder(ItemStack input, ItemStack output, int expAmount){
        super(expSerializer("experience_block"));
        this.input = input;
        this.output = output;
        this.expAmount = expAmount;
    }

    public static ExperienceBlockRecipeBuilder experienced(ItemStack input, ItemStack output, int expAmount){
        if(output.isEmpty()){
            throw new IllegalArgumentException("Experienced Recipe requires an output");
        }
        return new ExperienceBlockRecipeBuilder(input, output, expAmount);
    }


    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return null;
    }
}

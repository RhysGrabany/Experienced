package com.rhysgrabany.experienced.recipe.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.recipe.recipes.RecipeCriterion;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class ExperiencedRecipeBuilder<B extends ExperiencedRecipeBuilder<B>> {

    protected static ResourceLocation expSerializer(String name){
        return new ResourceLocation(Constants.MOD_ID, name);
    }


    protected final List<ICondition> conditions = new ArrayList<>();
    protected final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    protected final ResourceLocation serializerName;

    protected ExperiencedRecipeBuilder(ResourceLocation serializerName){
        this.serializerName = serializerName;
    }

    public B addCriterion(RecipeCriterion criterion){
        return addCriterion(criterion.name, criterion.criterion);
    }

    public B addCriterion(String name, ICriterionInstance criterion){
        advancementBuilder.withCriterion(name, criterion);
        return (B) this;
    }

    public B addCondition(ICondition condition){
        conditions.add(condition);
        return (B) this;
    }


    protected boolean hasCriterion(){
        return !advancementBuilder.getCriteria().isEmpty();
    }

    protected void validate(ResourceLocation id){}


}

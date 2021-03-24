package com.rhysgrabany.experienced.recipe.builder;

import com.google.gson.JsonObject;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.recipe.impl.ExperienceBlockIRecipe;
import com.rhysgrabany.experienced.setup.ModRecipeSerializers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ExperienceBlockRecipeBuilder extends ExperiencedRecipeBuilder<ExperienceBlockRecipeBuilder>{

    private final Ingredient input;
    private final Item output;
    private final int expAmount;
    private String group;
    private final IRecipeSerializer<ExperienceBlockIRecipe> recipeSerializer = ModRecipeSerializers.EXPERIENCE_BLOCK_RECIPE.get();

    protected ExperienceBlockRecipeBuilder(Ingredient input, Item output, int expAmount){
        super(expSerializer("experience_block"));
        this.input = input;
        this.output = output;
        this.expAmount = expAmount;

    }

    public static ExperienceBlockRecipeBuilder experienced(Ingredient input, Item output, int expAmount){
        if(output == null){
            throw new IllegalArgumentException("Experienced Recipe requires an output");
        }
        return new ExperienceBlockRecipeBuilder(input, output, expAmount);
    }

    public void build(Consumer<IFinishedRecipe> consumerIn) {
        this.build(consumerIn, Registry.ITEM.getKey(this.output));
    }

    public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.output);
        ResourceLocation resourcelocation1 = new ResourceLocation(save);
        if (resourcelocation1.equals(resourcelocation)) {
            throw new IllegalStateException("Recipe " + resourcelocation1 + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, resourcelocation1);
        }
    }

    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        this.validate(id);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumerIn.accept(new ExperienceBlockRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.input, this.output, this.expAmount, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.output.getGroup().getPath() + "/" + id.getPath()), this.recipeSerializer));
    }

    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return null;
    }


    public static class Result implements IFinishedRecipe{
        private final ResourceLocation id;
        private final String group;
        private final Ingredient input;
        private final Item result;
        private final int expAmount;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;
        private final IRecipeSerializer<?> serializer;

        public Result(ResourceLocation idIn, String groupIn, Ingredient input, Item result, int expAmount, Advancement.Builder advancementBuilderIn
                , ResourceLocation advancementIdIn, IRecipeSerializer<?> serializerIn) {

            this.id = idIn;
            this.group = groupIn;
            this.input = input;
            this.result = result;
            this.expAmount = expAmount;
            this.advancementBuilder = advancementBuilderIn;
            this.advancementId = advancementIdIn;
            this.serializer = serializerIn;

        }

        @Override
        public void serialize(JsonObject json) {
            json.add(Constants.Json.INPUT, input.serialize());
            json.addProperty(Constants.Json.OUTPUT, Registry.ITEM.getKey(this.result).toString());
            json.addProperty(Constants.Json.EXP, expAmount);
        }

        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return this.serializer;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return this.advancementBuilder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return this.advancementId;
        }
    }










}

package com.rhysgrabany.experienced.recipe;

import com.rhysgrabany.experienced.Experienced;
import jdk.internal.jline.internal.Nullable;
import net.minecraft.block.AnvilBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;

import java.util.*;

public class ExperiencedRecipeType<RT extends ExperiencedRecipe> implements IRecipeType<RT> {

    private static final List<ExperiencedRecipeType<? extends ExperiencedRecipe>> types = new ArrayList<>();

    public static final ExperiencedRecipeType<ExperienceBlockRecipe> EXPERIENCE_BLOCK = //create("exp_block");


    static <T extends IRecipe<?>> IRecipeType<T> register(final String key) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(key), new IRecipeType<T>() {
            public String toString() {
                return key;
            }
        });
    }

    IRecipeSerializer

    default <C extends IInventory> Optional<T> matches(IRecipe<C> recipe, World worldIn, C inv) {
        return recipe.matches(inv, worldIn) ? Optional.of((T)recipe) : Optional.empty();
    }


    private List<RT> cachedRecipes = Collections.emptyList();
    private final ResourceLocation regName;

    private ExperiencedRecipeType(String name){
        this.regName = Experienced.getId(name);
    }

    private static <RT extends ExperiencedRecipe> ExperiencedRecipeType<RT> create(String name){
        ExperiencedRecipeType<RT> type = new ExperiencedRecipeType<>(name);
        types.add(type);
        return type;
    }

    @Override
    public String toString() {
        return regName.toString();
    }


}

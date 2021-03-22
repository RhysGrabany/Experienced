package com.rhysgrabany.experienced.recipe.recipes;

import com.rhysgrabany.experienced.recipe.recipes.ExperiencedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class ExperienceBlockRecipe extends ExperiencedRecipe {


    private final ItemStack input;
    private final ItemStack output;
    private final int expAmount;

    public ExperienceBlockRecipe(ResourceLocation id, ItemStack input, ItemStack output, int expAmount){
        super(id);

        this.input = input;
        this.output = output.copy();
        this.expAmount = expAmount;

    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    @Override
    public void write(PacketBuffer buff) {

        buff.writeItemStack(input);
        buff.writeItemStack(output);
        buff.writeInt(expAmount);

    }

    public ItemStack getInput() {
        return input;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output.copy();
    }

    public int getExpAmount() {
        return expAmount;
    }
}

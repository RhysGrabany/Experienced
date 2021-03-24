package com.rhysgrabany.experienced.recipe.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class ExperienceBlockRecipe extends ExperiencedRecipe {


    private final Ingredient input;
    private final ItemStack output;
    private final int expAmount;

    public ExperienceBlockRecipe(ResourceLocation id, Ingredient input, ItemStack output, int expAmount){
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

        input.write(buff);
        buff.writeItemStack(output);
        buff.writeInt(expAmount);

    }

    public Ingredient getInput() {
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

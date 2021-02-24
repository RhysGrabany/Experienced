package com.rhysgrabany.experienced.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class ExperienceDrainRecipe extends ExperiencedRecipe {


    private final ItemStack input;
    private final ItemStack output;

    public ExperienceDrainRecipe(ResourceLocation id, ItemStack input, ItemStack output){
        super(id);

        this.input = input;
        this.output = output.copy();
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
    public ItemStack getRecipeOutput() {
        return output.copy();
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


    }
}

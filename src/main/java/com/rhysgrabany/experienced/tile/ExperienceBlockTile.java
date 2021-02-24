package com.rhysgrabany.experienced.tile;

import com.rhysgrabany.experienced.ModTiles;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContainer;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContents;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockStateData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ExperienceBlockTile extends BaseTile implements INamedContainerProvider, ITickableTileEntity {


    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int EXP_BAR_SLOT = 1;
    public static final int TOTAL_SLOTS = INPUT_SLOTS + OUTPUT_SLOTS;

    private ExperienceBlockContents inputContents;
    private ExperienceBlockContents outputContents;
    private ExperienceBlockContents expBarContents;

    private ItemStack currentlyDrainingItemLastTick = ItemStack.EMPTY;

    private final int MAX_EXP;

    private final ExperienceBlockStateData experienceBlockStateData = new ExperienceBlockStateData();


    // Constructor that creates the tile and the input/output contents
    public ExperienceBlockTile() {
        super(ModTiles.EXPERIENCE_BLOCK_TILE);

        inputContents = ExperienceBlockContents.createForTileEntity(INPUT_SLOTS,
                this::canPlayerAccessInventory,
                this::markDirty);

        outputContents = ExperienceBlockContents.createForTileEntity(OUTPUT_SLOTS,
                this::canPlayerAccessInventory,
                this::markDirty);

        expBarContents = ExperienceBlockContents.createForTileEntity(EXP_BAR_SLOT,
                this::canPlayerAccessInventory,
                this::markDirty);

        this.MAX_EXP = ExperienceBlock.MAX_EXP;


    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("experience_block_container");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return ExperienceBlockContainer.createContainerServerSide(p_createMenu_1_,
                p_createMenu_2_, inputContents, outputContents, expBarContents, experienceBlockStateData);
    }

    // Basically just checks to see if the player is within range, true if they are
    public boolean canPlayerAccessInventory(PlayerEntity playerIn){
        if(this.world.getTileEntity(this.pos) != this){
            return false;
        }

        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DIST_SQ = 8.0 * 8.0;

        return playerIn.getDistanceSq(pos.getX() + X_CENTRE_OFFSET,
                pos.getY() + Y_CENTRE_OFFSET,
                pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DIST_SQ;
    }

    @Override
    public void tick() {

        // do nothing on client
        if(world.isRemote) return;

        ItemStack currentlyDrainingItem = getCurrentDrainedItem();

        if(!ItemStack.areItemsEqual(currentlyDrainingItem, currentlyDrainingItemLastTick)){
            experienceBlockStateData.expDrainElapsed = 0;
        }

        currentlyDrainingItemLastTick = currentlyDrainingItem.copy();

        if(!currentlyDrainingItem.isEmpty()){
            boolean isSomethingDraining = drainExp();


            if(isSomethingDraining){
                experienceBlockStateData.expDrainElapsed += 1;
            }

            if(experienceBlockStateData.expDrainElapsed < 0) experienceBlockStateData.expDrainElapsed = 0;

            int drainTimeForItem = getItemDrainTime(this.world, currentlyDrainingItem);
            experienceBlockStateData.expDrainToComplete = drainTimeForItem;


        }

        markDirty();


    }

    public boolean willItemStackFit(ExperienceBlockContents experienceBlockContents, int slotIndex, ItemStack itemStackOrigin){
        ItemStack itemStackDest = experienceBlockContents.getStackInSlot(slotIndex);

        if(itemStackDest.isEmpty() || itemStackOrigin.isEmpty()){
            return true;
        }

        if(!itemStackOrigin.isItemEqual(itemStackDest)){
            return false;
        }

        return false;
    }

    private final String INPUT_SLOT_NBT = "inputSlot";
    private final String OUTPUT_SLOT_NBT = "outputSlot";
    private final String EXP_BAR_NBT = "expBar";

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        experienceBlockStateData.putIntoNBT(compound);

        compound.put(INPUT_SLOT_NBT, inputContents.serializeNBT());
        compound.put(OUTPUT_SLOT_NBT, outputContents.serializeNBT());
        compound.put(EXP_BAR_NBT, expBarContents.serializeNBT());

        return compound;
    }


    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);

        experienceBlockStateData.readFromNBT(nbt);

        CompoundNBT invNBT = nbt.getCompound(INPUT_SLOT_NBT);
        inputContents.deserializeNBT(invNBT);

        invNBT = nbt.getCompound(OUTPUT_SLOT_NBT);
        outputContents.deserializeNBT(invNBT);

        invNBT = nbt.getCompound(EXP_BAR_NBT);
        expBarContents.deserializeNBT(invNBT);

        if(inputContents.getSizeInventory() != INPUT_SLOTS
                || outputContents.getSizeInventory() != OUTPUT_SLOTS
                || expBarContents.getSizeInventory() != EXP_BAR_SLOT){
            throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected");
        }

    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {

        CompoundNBT updateTagTileEntityState = getUpdateTag();
        final int METADATA = 42;
        return new SUpdateTileEntityPacket(this.pos, METADATA, updateTagTileEntityState);


    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT updateTagTileEntityState = pkt.getNbtCompound();
        BlockState state = world.getBlockState(pos);
        handleUpdateTag(state, updateTagTileEntityState);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    public void dropContents(World world, BlockPos pos){
        InventoryHelper.dropInventoryItems(world, pos, inputContents);
        InventoryHelper.dropInventoryItems(world, pos, outputContents);
    }

    // Checks if there is an item in the input area being drained
    public boolean isExpBeingDrained(){
        if(experienceBlockStateData.expDrainTimeRemaining > 0) return true;
        return false;
    }

    private ItemStack getCurrentDrainedItem(){
        return drainFirstSuitableInputItem(false);
    }

    private void drainFirstSuitableInputItem(){
        drainFirstSuitableInputItem(true);
    }

    private ItemStack drainFirstSuitableInputItem(boolean performDrain){

        Integer firstSuitableInputSlot = null;
        Integer firstSuitableOutputSlot = null;
        ItemStack result = ItemStack.EMPTY;

        ItemStack itemToDrain = inputContents.getStackInSlot(0);

        if(!itemToDrain.isEmpty()){
            result = getDrainResultForItem(this.world, itemToDrain);

            if(!result.isEmpty()){
                if(willItemStackFit(outputContents, 0, result)){
                    firstSuitableInputSlot = 0;
                    firstSuitableOutputSlot = 0;
                }
            }

        }

        if(firstSuitableInputSlot == null){
            return ItemStack.EMPTY;
        }

        ItemStack returnValue = inputContents.getStackInSlot(firstSuitableInputSlot).copy();

        if(!performDrain){
            return returnValue;
        }

        inputContents.decrStackSize(firstSuitableInputSlot, 1);
        outputContents.incrStackSize(firstSuitableOutputSlot, result);

        markDirty();
        return returnValue;


    }

    private ItemStack getDrainResultForItem(World world, ItemStack item){

        item.getOrCreateTag().putInt("exp", 0);

        return item;
    }

//    private boolean takeExperience(ItemStackHandler itemHandler){
//        if(itemHandler == null){
//            return false;
//        }
//
//        int expToTake = itemHandler.getStackInSlot(0).getOrCreateTag().getInt("exp");
//
//        if(expToTake > 0){
//
//
//        }
//
//    }

    private boolean drainExp(){
        boolean expDrain = false;
        boolean inventoryChanged = false;

        if(experienceBlockStateData.expDrainTimeRemaining > 0){
            --experienceBlockStateData.expDrainTimeRemaining;
            expDrain = true;
        }

        if(experienceBlockStateData.expDrainTimeRemaining == 0){
            ItemStack expDrainItemStack = inputContents.getStackInSlot(0);
            if(!expDrainItemStack.isEmpty() && getItemDrainTime(this.world, expDrainItemStack) > 0){
                int drainTimeForItem = getItemDrainTime(this.world, expDrainItemStack);

                experienceBlockStateData.expDrainTimeRemaining = drainTimeForItem;
                experienceBlockStateData.expDrainTimeInitialValue = drainTimeForItem;

                inventoryChanged = true;


//                if(expDrainItemStack.isEmpty()){
//
//                }

            }
        }

        if(inventoryChanged) markDirty();

        return expDrain;
    }

    public static int getItemDrainTime(World world, ItemStack stack){

        return getDrainTime(stack);


    }

    private static int getDrainTime(ItemStack item){
        int amount = item.getOrCreateTag().getInt("exp");
        return (amount / getDrainTimeMultiplier());

    }

    private static int getDrainTimeMultiplier(){
        switch (ExperienceBlock.BLOCK_TIER){
            case SMALL:
                return 8;
            case MEDIUM:
                return 12;
            case LARGE:
                return 16;
            case CREATIVE:
                return 24;
        }
        return 0;
    }



//    private int insertExp(int container, @Nonnull int amount, @Nonnull ActionResultType action){
//
//    }

//    public static ItemStack getDrainResultForItem(World world, ItemStack stack){
//
//        Optional<ExperienceDrainRecipe> matchingRecipe = getMatchingRecipeForInput(world, stack);
//        if(!matchingRecipe.isPresent()) return ItemStack.EMPTY;
//
//        return matchingRecipe.get().getRecipeOutput().copy();
//
//
//    }
//
//    public static Optional<ExperienceDrainRecipe> getMatchingRecipeForInput(World world, ItemStack stack){
//        RecipeManager recipeManager = world.getRecipeManager();
//        Inventory singleItemInventory = new Inventory(stack);
//
//
//        Optional<ExperienceDrainRecipe> matchingRecipe = recipeManager.getRecipe(IRecipeType.DRAIN, singleItemInventory, world);
//        return matchingRecipe;
//
//
//
//
//    }















}

package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Predicate;

public class ExperienceBlockContents implements IInventory {

    private ItemStackHandler expBlockComponentContents;

    //region Constructors

    // Two constructors to coincide with the two methods needed for Client/Server side
    private ExperienceBlockContents(int size){
        this.expBlockComponentContents = new ItemStackHandler(size);
    }

    private ExperienceBlockContents(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLam,
                                    Notify markDirtyNotificationLam){

        this.expBlockComponentContents = new ItemStackHandler(size);
        this.canPlayerAccessInventoryLam = canPlayerAccessInventoryLam;
        this.markDirtyNotificationLam = markDirtyNotificationLam;

    }

    //endregion

    //region Gui Stuff

    // Two methods needed for Client and Server side separately, TileEntity for the server
    // and Container for the Client
    public static ExperienceBlockContents createForTileEntity(int size,
                                                              Predicate<PlayerEntity> canPlayerAccessInventoryLam,
                                                              Notify markDirtyNotificationLam){
        return new ExperienceBlockContents(size, canPlayerAccessInventoryLam, markDirtyNotificationLam);
    }

    public static ExperienceBlockContents createForClientSideContainer(int size){
        return new ExperienceBlockContents(size);
    }

    //endregion

    //region Write/Read NBT

    public CompoundNBT serializeNBT(){
        return expBlockComponentContents.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt){
        expBlockComponentContents.deserializeNBT(nbt);
    }

    //endregion

    //region Inventory Manipulation

    @Override
    public int getSizeInventory() {
        return expBlockComponentContents.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < expBlockComponentContents.getSlots(); i++){
            if(!expBlockComponentContents.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return expBlockComponentContents.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(count < 0){
            throw new IllegalArgumentException("count should be >= 0: " + count);
        }
        return expBlockComponentContents.extractItem(index, count, false);
    }

    public ItemStack incrStackSize(int index, ItemStack itemToInsert){
        ItemStack leftoverItemStack = expBlockComponentContents.insertItem(index, itemToInsert, false);
        return leftoverItemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        int maxItemStackSize = expBlockComponentContents.getSlotLimit(index);
        return expBlockComponentContents.extractItem(index, maxItemStackSize, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        expBlockComponentContents.setStackInSlot(index, stack);
    }

    @Override
    public void clear() {
        for(int i = 0; i < expBlockComponentContents.getSlots(); i++){
            expBlockComponentContents.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    //endregion

    //region Functional Methods Using Linking Methods

    @FunctionalInterface
    public interface Notify{
        void invoke();
    }


    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return canPlayerAccessInventoryLam.test(player);
    }

    @Override
    public void markDirty() {
        markDirtyNotificationLam.invoke();
    }

    @Override
    public void openInventory(PlayerEntity player) {
        openInventoryNotificationLam.invoke();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        closeInventoryNotificationLam.invoke();
    }

    //endregion

    //region Predicates

    //region Linking Methods

    // Server Side Only: Set function that the conatiner should call if the player wants to access the block
    public void setCanPlayerAccessInventoryLam(Predicate<PlayerEntity> canPlayerAccessInventoryLam){
        this.canPlayerAccessInventoryLam = canPlayerAccessInventoryLam;
    }

    // Function to tell the parent TileEntity that something has changed and to update, else do nothing
    public void setMarkDirtyNotificationLam(Notify markDirtyNotificationLam){
        this.markDirtyNotificationLam = markDirtyNotificationLam;
    }

    // Function to tell the parent Tile that a player has opened the container, else do nothing
    public void setOpenInventoryNotificationLam(Notify openInventoryNotificationLam){
        this.openInventoryNotificationLam = openInventoryNotificationLam;
    }

    // Function to tell the parent Tile that a player has closed the container, else do nothing
    public void setCloseInventoryNotificationLam(Notify closeInventoryNotificationLam){
        this.closeInventoryNotificationLam = closeInventoryNotificationLam;
    }

    //endregion

    //region Preds

    private Predicate<PlayerEntity> canPlayerAccessInventoryLam = x -> true;

    private Notify markDirtyNotificationLam = () -> {};

    private Notify openInventoryNotificationLam = () -> {};

    private Notify closeInventoryNotificationLam = () -> {};

    //endregion

    //endregion









}

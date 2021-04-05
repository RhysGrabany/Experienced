package com.rhysgrabany.experienced.tile;

import com.rhysgrabany.experienced.ModTiles;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContainer;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContents;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockStateData;
import com.rhysgrabany.experienced.network.NetworkHandler;
import com.rhysgrabany.experienced.network.messages.GiveExpToPlayerServer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExperienceBlockTile extends BaseTile implements INamedContainerProvider, ITickableTileEntity {


    public static final int INPUT_SLOTS = 1;
    public static final int INPUT_BOOK_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int EXP_BAR_SLOT = 1;
    public static final int TOTAL_SLOTS = INPUT_SLOTS + OUTPUT_SLOTS;

    private ExperienceBlockContents inputContents;
    private ExperienceBlockContents inputBookContents;
    private ExperienceBlockContents outputContents;
    private ExperienceBlockContents expBarContents;

    private ItemStack currentlyInfusingItemLastTick = ItemStack.EMPTY;
    private ItemStack currentlyExtractionItemLastTick = ItemStack.EMPTY;

    public ExperienceBlock.Tier tier;
    public ExperienceBlockTile tile;

    private final ExperienceBlockStateData experienceBlockStateData;


    // Constructor that creates the experienceBlockTile and the input/output contents
    public ExperienceBlockTile(ExperienceBlock.Tier tier) {
        super(getTier(tier));

        inputContents = ExperienceBlockContents.createForTileEntity(INPUT_SLOTS,
                this::canPlayerAccessInventory,
                this::markDirty);

        inputBookContents = ExperienceBlockContents.createForTileEntity(INPUT_BOOK_SLOTS,
                this::canPlayerAccessInventory,
                this::markDirty);

        outputContents = ExperienceBlockContents.createForTileEntity(OUTPUT_SLOTS,
                this::canPlayerAccessInventory,
                this::markDirty);

        expBarContents = ExperienceBlockContents.createForTileEntity(EXP_BAR_SLOT,
                this::canPlayerAccessInventory,
                this::markDirty);

        this.tier = tier;
        this.tile = (ExperienceBlockTile) getTileEntity();

        this.experienceBlockStateData = new ExperienceBlockStateData();

    }



    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("experience_block_container");
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerIn, PlayerEntity playerEn) {
        return ExperienceBlockContainer.createContainerServerSide(windowId, playerIn, tile,
                inputContents, inputBookContents, outputContents, expBarContents, experienceBlockStateData);
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


        ItemStack currentItemExpInfuse = getCurrentInfuseItemInput();
        ItemStack currentExtractionItemInput = getCurrentExpExtractItemInput();


        if(!currentExtractionItemInput.isEmpty()){
            extractExpItem();
        }

        currentlyExtractionItemLastTick = currentExtractionItemInput.copy();

//        markDirty();
    }

    public static boolean doesItemHaveExpTag(ItemStack item){
        return item.getOrCreateTag().contains("exp");
    }

    private ItemStack getCurrentInfuseItemInput(){
        return infuseFirstSuitableInputItem(false);
    }

    private void infuseFirstSuitableInputItem(){
        infuseFirstSuitableInputItem(true);
    }

    private ItemStack infuseFirstSuitableInputItem(boolean performInfuse){

        Integer inputSlot = null;
        Integer outputSlot = null;

        return ItemStack.EMPTY;
    }


    private ItemStack getCurrentExpExtractItemInput(){
        return extractExpItem(false);
    }

    private void extractExpItem(){
        extractExpItem(true);
    }

    private ItemStack extractExpItem(boolean performExtract){

        ItemStack extractItem = inputBookContents.getStackInSlot(0);

        if(!performExtract){
            return extractItem;
        }

        int extractRate = getExtractRate();
        performExtraction(extractItem, extractRate);

        currentlyExtractionItemLastTick = extractItem;

        markDirty();
        return extractItem;

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return null;
    }

    private void performExtraction(ItemStack extractItem, int extractRate){

        // Store the amount of exp currently in the book, and also the amount of exp that will be stored
        int expAmount = extractItem.getOrCreateTag().getInt("exp");
        int expBlockAmount = getExpBlockAmount();
        int expBlockMaxAmount = getMaxExpAmount();

        int extractedExp;

        // Base case for the lower, and upper end
        if(expAmount == 0 || expBlockAmount == expBlockMaxAmount){
            return;
        }

        // If the extract rate is higher than the amount of exp stored, then it'll go into the minus territory
        // If expAmount is less than the extractRate then the amount will be saved, otherwise just use the extractRate
        extractedExp = (expAmount < extractRate) ? expAmount : extractRate;


        // If the amount of exp being extracted goes over the cap in the exp block then bring it down
        if(expBlockAmount + extractedExp > expBlockMaxAmount){
            extractedExp = expBlockMaxAmount - expBlockAmount;
        }


        expAmount -= extractedExp;

        extractItem.getTag().putInt("exp", expAmount);
        addExpAmount(extractedExp);

//        BlockState state = world.getBlockState(this.pos);
//        world.notifyBlockUpdate(this.pos, state, state, 3);
//        markDirty();

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
    private final String INPUT_BOOK_SLOT_NBT = "inputBookSlot";
    private final String OUTPUT_SLOT_NBT = "outputSlot";
    private final String EXP_BAR_NBT = "expBar";

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        experienceBlockStateData.putIntoNBT(compound);

        compound.put(INPUT_SLOT_NBT, inputContents.serializeNBT());
        compound.put(INPUT_BOOK_SLOT_NBT, inputBookContents.serializeNBT());
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

        invNBT = nbt.getCompound(INPUT_BOOK_SLOT_NBT);
        inputBookContents.deserializeNBT(invNBT);

        invNBT = nbt.getCompound(OUTPUT_SLOT_NBT);
        outputContents.deserializeNBT(invNBT);

        invNBT = nbt.getCompound(EXP_BAR_NBT);
        expBarContents.deserializeNBT(invNBT);

        if(inputContents.getSizeInventory() != INPUT_SLOTS
                || inputBookContents.getSizeInventory() != INPUT_BOOK_SLOTS
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
        InventoryHelper.dropInventoryItems(world, pos, inputBookContents);
        InventoryHelper.dropInventoryItems(world, pos, outputContents);
    }



    public static boolean isItemValidForInputSlot(ItemStack item){
        return true;
    }

    public static boolean isItemValidForOutputSlot(ItemStack item){
        return true;
    }



    public int getExpBlockAmount(){
        return experienceBlockStateData.expAmountInContainer;
    }


    //region Exp Manipulation for Player and Block
    public void givePlayerExpAmount(int value){
        GiveExpToPlayerServer giveExp = new GiveExpToPlayerServer(value);
        NetworkHandler.channel.sendToServer(giveExp);
    }

    // Methods for the Exp Manipulation for adding and removing exp from the player to the ExpBlock
    // Add Exp to the Block
    public void addExpAmount(int value){

        // Things tried when fixing:
        // markDirty by itself doesnt work
        // just value addition works but doesn't save the exp to the player cause it is client side only
        // Network Handler fixes issue of giving the ServerPlayerEntity the exp, now to try and fix the issue with book input exp


        experienceBlockStateData.expAmountInContainer += value;
        markDirty();
    }

    // Take Exp from the Block
    public void takeExpAmount(int value){
        experienceBlockStateData.expAmountInContainer -= value;
        markDirty();
    }
    //endregion


    //region Getting General Information
    public static TileEntityType<ExperienceBlockTile> getTier(ExperienceBlock.Tier tier){
        switch (tier){
            case SMALL:
                return ModTiles.EXPERIENCE_BLOCK_TILES.get(ExperienceBlock.Tier.SMALL).get();
            case MEDIUM:
                return ModTiles.EXPERIENCE_BLOCK_TILES.get(ExperienceBlock.Tier.MEDIUM).get();
            case LARGE:
                return ModTiles.EXPERIENCE_BLOCK_TILES.get(ExperienceBlock.Tier.LARGE).get();
            case CREATIVE:
                return ModTiles.EXPERIENCE_BLOCK_TILES.get(ExperienceBlock.Tier.CREATIVE).get();
            default:
                throw new IllegalArgumentException("Unknown tier: " + tier);
        }
    }

    public int getMaxExpAmount(){
        return getMaxExpFromTier(tier);
    }

    public int getMaxExpFromTier(ExperienceBlock.Tier tier){
        switch(tier){
            case SMALL:
                return 1395;
            case MEDIUM:
                return 8670;
            case LARGE:
                return 30970;
            case CREATIVE:
                return Integer.MAX_VALUE;
            default:
                throw new IllegalArgumentException("Tier is not recognized: " + tier);
        }
    }

    private int getExtractRate(){
        switch (this.tier){
            case SMALL:
                return 1;
            case MEDIUM:
                return 2;
            case LARGE:
                return 3;
            case CREATIVE:
                return 10;
            default:
                throw new IllegalArgumentException("Unknown tier: " + tier);
        }
    }
    //endregion











}

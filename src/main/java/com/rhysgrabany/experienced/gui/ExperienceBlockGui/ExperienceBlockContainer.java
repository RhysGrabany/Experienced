package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import com.rhysgrabany.experienced.ModContainers;
import com.rhysgrabany.experienced.block.ExperienceBlock;
import com.rhysgrabany.experienced.capabilities.ModCapabilities;
import com.rhysgrabany.experienced.capabilities.experience.IExperienceStorage;
import com.rhysgrabany.experienced.gui.BaseContainer;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import com.rhysgrabany.experienced.util.ExperienceHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ExperienceBlockContainer extends BaseContainer {

    // Vanilla Inventory Basic slot counts -- 9 Slots for hotbar, 3x9 for inventory
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    // Vanilla Inventory Indexes
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int HOTBAR_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX;
    private static final int PLAYER_INVENTORY_FIRST_SLOT_INDEX = HOTBAR_FIRST_SLOT_INDEX + HOTBAR_SLOT_COUNT;


    // Experience Block Inventory slot counts
    public static final int INPUT_SLOTS = ExperienceBlockTile.INPUT_SLOTS;
    public static final int INPUT_BOOK_SLOTS = ExperienceBlockTile.INPUT_BOOK_SLOTS;
    public static final int OUTPUT_SLOTS = ExperienceBlockTile.OUTPUT_SLOTS;
    public static final int EXP_BAR_SLOT = ExperienceBlockTile.EXP_BAR_SLOT;
    // Experience Block Inventory Indexes
    private static final int INPUT_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int INPUT_BOOK_SLOT_INDEX = INPUT_SLOT_INDEX + INPUT_BOOK_SLOTS;
    private static final int OUTPUT_SLOT_INDEX = INPUT_BOOK_SLOT_INDEX + OUTPUT_SLOTS;
    private static final int EXP_BAR_SLOT_INDEX = OUTPUT_SLOT_INDEX + EXP_BAR_SLOT;


    // Gui Pos of the player inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 8;
    public static final int PLAYER_INVENTORY_YPOS = 84;

    private World world;

    private ExperienceBlockContents inputContents;
    private ExperienceBlockContents inputBookContents;
    private ExperienceBlockContents outputContents;
    private ExperienceBlockContents expBarContents;

    private ExperienceBlockStateData experienceBlockStateData;

    private PlayerEntity player;

    private ExperienceBlockTile experienceBlockTile;

    public ExperienceBlock.Tier tier;


    public ExperienceBlockContainer(int windowId, PlayerInventory playerIn){
        super(ModContainers.EXPERIENCE_BLOCK_CONTAINER.get(), windowId);
        this.player = playerIn.player;
    }

    public static ExperienceBlockContainer createContainerServerSide(int windowId, PlayerInventory playerInventory, ExperienceBlockTile tile,
                                                                     ExperienceBlockContents inputZoneContents,
                                                                     ExperienceBlockContents inputBookZoneContents,
                                                                     ExperienceBlockContents outputZoneContents,
                                                                     ExperienceBlockContents expBarZoneContents,
                                                                     ExperienceBlockStateData experienceBlockStateData) {

        return new ExperienceBlockContainer(windowId, playerInventory, tile, inputZoneContents, inputBookZoneContents, outputZoneContents, expBarZoneContents, experienceBlockStateData);
    }

    public static ExperienceBlockContainer createContainerClientSide(int windowId, PlayerInventory playerInventory, ExperienceBlockTile tile) {

        ExperienceBlockContents inputZoneContents = ExperienceBlockContents.createForClientSideContainer(INPUT_SLOTS);
        ExperienceBlockContents inputBookZoneContents = ExperienceBlockContents.createForClientSideContainer(INPUT_BOOK_SLOTS);
        ExperienceBlockContents outputZoneContents = ExperienceBlockContents.createForClientSideContainer(OUTPUT_SLOTS);
        ExperienceBlockContents expBarZoneContents = ExperienceBlockContents.createForClientSideContainer(EXP_BAR_SLOT);
        ExperienceBlockStateData experienceBlockStateData = new ExperienceBlockStateData();


        return new ExperienceBlockContainer(windowId, playerInventory, tile, inputZoneContents, inputBookZoneContents, outputZoneContents, expBarZoneContents, experienceBlockStateData);
    }


    public ExperienceBlockContainer(int windowId, PlayerInventory playerIn, @Nullable ExperienceBlockTile tile, ExperienceBlockContents inputZoneContents,
                                    ExperienceBlockContents inputBookZoneContents, ExperienceBlockContents outputZoneContents,
                                    ExperienceBlockContents expBarZoneContents, ExperienceBlockStateData experienceBlockStateData) {

        super(ModContainers.EXPERIENCE_BLOCK_CONTAINER.get(), windowId);

        if (ModContainers.EXPERIENCE_BLOCK_CONTAINER.get() == null) {
            throw new IllegalStateException("Must Initialise ContainerType ExperienceBlockContainer before constructing a ExperienceBlockContainer!");
        }

        this.inputContents = inputZoneContents;
        this.inputBookContents = inputBookZoneContents;
        this.outputContents = outputZoneContents;
        this.expBarContents = expBarZoneContents;

        this.experienceBlockStateData = experienceBlockStateData;

        this.player = playerIn.player;

        this.tier = tile.tier;

        this.world = playerIn.player.world;
        this.experienceBlockTile = tile;

        trackIntArray(experienceBlockStateData);


        // The amount of pixels from the first pixel of one slot to the next slot
        final int SLOT_SPACING_X = 18;
        final int SLOT_SPACING_Y = 18;

        // Gui Pos of the player hotbar
        final int HOTBAR_XPOS = 8;
        final int HOTBAR_YPOS = 142;

        // Adding the player's hotbar to the gui - (x,y) loc of each item
        for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
            addSlot(new Slot(playerIn, x, HOTBAR_XPOS + SLOT_SPACING_X * x, HOTBAR_YPOS));
        }

        // Adding the rest of the player's inventory
        for (int y = 0; y < PLAYER_INVENTORY_ROW_COUNT; y++) {
            for (int x = 0; x < PLAYER_INVENTORY_COLUMN_COUNT; x++) {
                int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;

                // Gets the location of the slot using the positions and slot spacing
                int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_SPACING_X;
                int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_SPACING_Y;

                addSlot(new Slot(playerIn, slotNumber, xpos, ypos));

            }
        }


        // Input Slot
        final int INPUT_SLOT_XPOS = 44;
        final int INPUT_SLOT_YPOS = 15;

        // This looks stupid just for one slot but it gives the chance to upgrade in the future if I really want to
        for (int i = 0; i < INPUT_SLOTS; i++) {
            addSlot(new SlotInput(inputZoneContents, i, INPUT_SLOT_XPOS + SLOT_SPACING_X * i, INPUT_SLOT_YPOS));
        }

        // Input Slot
        final int INPUT_BOOK_SLOT_XPOS = 77;
        final int INPUT_BOOK_SLOT_YPOS = 32;

        // This looks stupid just for one slot but it gives the chance to upgrade in the future if I really want to
        for (int i = 0; i < INPUT_SLOTS; i++) {
            addSlot(new SlotInputBook(inputBookZoneContents, i, INPUT_BOOK_SLOT_XPOS + SLOT_SPACING_X * i, INPUT_BOOK_SLOT_YPOS));
        }


        // Output Slot
        final int OUTPUT_SLOT_XPOS = 44;
        final int OUTPUT_SLOT_YPOS = 50;

        for (int i = 0; i < OUTPUT_SLOTS; i++) {
            addSlot(new SlotOutput(outputZoneContents, i, OUTPUT_SLOT_XPOS + SLOT_SPACING_X * i, OUTPUT_SLOT_YPOS));
        }

        // Exp Slot
//        final int EXP_SLOT_SPACING_X = 21;
//        final int EXP_SLOT_SPACING_Y = 72;
//
//        final int EXP_SLOT_XPOS = 8;
//        final int EXP_SLOT_YPOS = 7;


    }




    public double fractionOfExpAmount(){
        IExperienceStorage cap = experienceBlockTile.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);
        int expAmount = cap.getExperienceStored();
        int expMaxAmount = cap.getMaxExperienceStored();
        if(expAmount == 0) return 0;
        double fraction = (double) expAmount / expMaxAmount;
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inputContents.isUsableByPlayer(playerIn) && outputContents.isUsableByPlayer(playerIn);
    }


    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot sourceSlot = inventorySlots.get(index);


        if(sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;

        ItemStack sourceItemStack = sourceSlot.getStack();
        ItemStack sourceStackBeforeMerge = sourceItemStack.copy();
        boolean successfulTransfer = false;

        SlotZone sourceZone = SlotZone.getZoneFromIndex(index);

        switch (sourceZone){
            case OUTPUT_ZONE:
            case INPUT_ZONE:
            case INPUT_BOOK_ZONE:
                successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
                if(!successfulTransfer){
                    successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
                }
                break;
            case PLAYER_HOTBAR:
            case PLAYER_MAIN_INVENTORY:
                if(ExperienceBlockTile.doesItemHaveExpCap(sourceItemStack)){
                    successfulTransfer = mergeInto(SlotZone.INPUT_BOOK_ZONE, sourceItemStack, false);
                }
                if(!successfulTransfer){
                    if(sourceZone == SlotZone.PLAYER_HOTBAR){
                        successfulTransfer = mergeInto(SlotZone.PLAYER_MAIN_INVENTORY, sourceItemStack, false);
                    } else {
                        successfulTransfer = mergeInto(SlotZone.PLAYER_HOTBAR, sourceItemStack, false);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected sourceZone: " + sourceZone);
        }

        if(!successfulTransfer) return ItemStack.EMPTY;

        if(sourceItemStack.isEmpty()){
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }

        if(sourceItemStack.getCount() == sourceStackBeforeMerge.getCount()){
            return ItemStack.EMPTY;
        }

        sourceSlot.onTake(playerIn, sourceItemStack);
        return sourceStackBeforeMerge;

    }

    private boolean mergeInto(SlotZone dest, ItemStack sourceItemStack, boolean fillFromEnd){
        return mergeItemStack(sourceItemStack, dest.firstIndex, dest.lastIndexPlus1, fillFromEnd);
    }







    private enum SlotZone{
        INPUT_ZONE(INPUT_SLOT_INDEX, INPUT_SLOTS),
        INPUT_BOOK_ZONE(INPUT_BOOK_SLOT_INDEX, INPUT_BOOK_SLOTS),
        OUTPUT_ZONE(OUTPUT_SLOT_INDEX, OUTPUT_SLOTS),
        EXP_ZONE(EXP_BAR_SLOT_INDEX, EXP_BAR_SLOT),
        PLAYER_MAIN_INVENTORY(PLAYER_INVENTORY_FIRST_SLOT_INDEX, PLAYER_INVENTORY_SLOT_COUNT),
        PLAYER_HOTBAR(HOTBAR_FIRST_SLOT_INDEX, HOTBAR_SLOT_COUNT);

        public final int firstIndex;
        public final int slotCount;
        public final int lastIndexPlus1;


        SlotZone(int firstIndex, int numberOfSlots){
            this.firstIndex = firstIndex;
            this.slotCount = numberOfSlots;
            this.lastIndexPlus1 = firstIndex + numberOfSlots;
        }

        public static SlotZone getZoneFromIndex(int slotIndex){
            for(SlotZone slotZone : SlotZone.values()){
                if(slotIndex >= slotZone.firstIndex && slotIndex < slotZone.lastIndexPlus1) return slotZone;
            }
            throw new IndexOutOfBoundsException("Unexpected slotIndex");
        }


    }

    //region Buttons for ExperienceBlockScreen

    //TODO: Can I move these into their own classes derived from the base buttons? Would cut down on some code

    // Plus Buttons ADD experience to the block
    // ADDING a single level to the block at a time
    public void singlePlusOnButtonPress(){

        IExperienceStorage blockCap = experienceBlockTile.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);
        int expLevel = player.experienceLevel;
        int expTotal = player.experienceTotal;

        // The amount of exp to take away from the player, test if they are at least 1 level,
        int expToTake = ExperienceHelper.takeExpToPrevLevel(expLevel, expTotal);
        int expTaken = blockCap.receiveExperience(expToTake, false);

        ExperienceHelper.givePlayerExpAmount(-expTaken);
        experienceBlockTile.sync(experienceBlockTile);
        experienceBlockTile.markDirty();

    }

    // ADDING all of the levels to the block
    public void doublePlusOnButtonPress(){

        IExperienceStorage blockCap = experienceBlockTile.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);

        int expTotal = player.experienceTotal;
        int expToGive = blockCap.receiveExperience(expTotal, false);

        ExperienceHelper.givePlayerExpAmount(-expToGive);
        experienceBlockTile.markDirty();

    }

    // Minus Buttons TAKE AWAY experience from the block
    // TAKE AWAY a single level from the block
    public void singleMinusOnButtonPress(){

        IExperienceStorage blockCap = experienceBlockTile.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);

        int expLevel = player.experienceLevel;

        int amountNeededToNextLevel = ExperienceHelper.recieveExpToNextLevel(expLevel);
        int expToTake = blockCap.extractExperience(amountNeededToNextLevel, false);

        ExperienceHelper.givePlayerExpAmount(expToTake);
        experienceBlockTile.sync(experienceBlockTile);
        experienceBlockTile.markDirty();

    }

    // TAKING AWAY all of the levels in the block
    public void doubleMinusOnButtonPress(){

        IExperienceStorage blockCap = experienceBlockTile.getCapability(ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY).orElse(null);
        int expToTake = blockCap.extractExperience(blockCap.getExperienceStored(), false);

        ExperienceHelper.givePlayerExpAmount(expToTake);
        experienceBlockTile.markDirty();

    }


    //endregion


    public class SlotInput extends Slot {

        public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return ExperienceBlockTile.isItemValidForInputSlot(stack);
        }
    }

    public class SlotInputBook extends Slot {

        public SlotInputBook(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return ExperienceBlockTile.isItemValidForInputSlot(stack);
        }
    }

    public class SlotOutput extends Slot {

        public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return ExperienceBlockTile.isItemValidForOutputSlot(stack);
        }
    }

    //TODO: Prob can delete the ExpSlot since it isn't going to be used...
    public class SlotExp extends Slot {

        public SlotExp(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();


        for(int i = 0; i < listeners.size(); ++i){
            IContainerListener listener = this.listeners.get(i);
        }


    }
}

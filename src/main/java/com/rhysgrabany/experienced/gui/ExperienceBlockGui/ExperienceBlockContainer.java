package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import com.rhysgrabany.experienced.ModContainers;
import com.rhysgrabany.experienced.gui.BaseContainer;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
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
    public static final int OUTPUT_SLOTS = ExperienceBlockTile.OUTPUT_SLOTS;
    public static final int EXP_BAR_SLOT = ExperienceBlockTile.EXP_BAR_SLOT;
    // Experience Block Inventory Indexes
    private static final int INPUT_SLOT_INDEX = PLAYER_INVENTORY_FIRST_SLOT_INDEX + INPUT_SLOTS;
    private static final int OUTPUT_SLOT_INDEX = INPUT_SLOT_INDEX + OUTPUT_SLOTS;
    private static final int EXP_BAR_SLOT_INDEX = OUTPUT_SLOT_INDEX + EXP_BAR_SLOT;


    // Gui Pos of the player inventory grid
    public static final int PLAYER_INVENTORY_XPOS = 8;
    public static final int PLAYER_INVENTORY_YPOS = 84;


    // Pos of Tile Label
    public static final int TILE_INV_YPOS = 20;
    public static final int PLAYER_INV_YPOS = 51;


    private World world;

    private ExperienceBlockContents inputContents;
    private ExperienceBlockContents outputContents;
    private ExperienceBlockContents expBarContents;


    public ExperienceBlockContainer(int windowId, PlayerInventory playerIn, ExperienceBlockContents inputZoneContents,
                                    ExperienceBlockContents outputZoneContents, ExperienceBlockContents expBarZoneContents,
                                    ExperienceBlockStateData experienceBlockStateData) {

        super(ModContainers.EXPERIENCE_BLOCK_CONTAINER.get(), windowId);

        if (ModContainers.EXPERIENCE_BLOCK_CONTAINER.get() == null) {
            throw new IllegalStateException("Must Initialise ContainerType ExperienceBlockContainer before constructing a ExperienceBlockContainer!");
        }

        this.inputContents = inputZoneContents;
        this.outputContents = outputZoneContents;
        this.expBarContents = expBarZoneContents;

        this.world = playerIn.player.world;

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


        // Output Slot
        final int OUTPUT_SLOT_XPOS = 44;
        final int OUTPUT_SLOT_YPOS = 50;

        for (int i = 0; i < OUTPUT_SLOTS; i++) {
            addSlot(new SlotOutput(outputZoneContents, i, OUTPUT_SLOT_XPOS + SLOT_SPACING_X * i, OUTPUT_SLOT_YPOS));
        }

        // Exp Slot
        final int EXP_SLOT_SPACING_X = 21;
        final int EXP_SLOT_SPACING_Y = 72;

        final int EXP_SLOT_XPOS = 8;
        final int EXP_SLOT_YPOS = 7;

        //for(int i = 0; i < EXP_BAR_SLOT; i++) {
//            addSlot(new SlotExp(expBarZoneContents, i, EXP_SLOT_XPOS + EXP_SLOT_SPACING_X * i, EXP_SLOT_YPOS));
//        }






    }


    private ExperienceBlockContainer(int windowId, PlayerInventory playerIn) {
        super(ModContainers.EXPERIENCE_BLOCK_CONTAINER.get(), windowId);
    }

    public static ExperienceBlockContainer createContainerServerSide(int windowId, PlayerInventory playerInventory,
                                                                     ExperienceBlockContents inputZoneContents,
                                                                     ExperienceBlockContents outputZoneContents,
                                                                     ExperienceBlockContents expBarZoneContents,
                                                                     ExperienceBlockStateData experienceBlockStateData) {

        return new ExperienceBlockContainer(windowId, playerInventory, inputZoneContents, outputZoneContents, expBarZoneContents, experienceBlockStateData);
    }

    public static ExperienceBlockContainer createContainerClientSide(int windowId, PlayerInventory playerInventory, PacketBuffer extraData) {
//        ChestContents chestContents = ChestContents.createForClientSideContainer(ModChestTileEntity.NUMBER_OF_SLOTS);

        ExperienceBlockContents inputZoneContents = ExperienceBlockContents.createForClientSideContainer(INPUT_SLOTS);
        ExperienceBlockContents outputZoneContents = ExperienceBlockContents.createForClientSideContainer(OUTPUT_SLOTS);
        ExperienceBlockContents expBarZoneContents = ExperienceBlockContents.createForClientSideContainer(EXP_BAR_SLOT);
        ExperienceBlockStateData experienceBlockStateData = new ExperienceBlockStateData();


        return new ExperienceBlockContainer(windowId, playerInventory, inputZoneContents, outputZoneContents, expBarZoneContents, experienceBlockStateData);
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return inputContents.isUsableByPlayer(playerIn) && outputContents.isUsableByPlayer(playerIn);
    }

    public class SlotInput extends Slot {

        public SlotInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

//        @Override
//        public boolean isItemValid(ItemStack stack) {
//            return ExperienceBlockTile
//        }
    }

    public class SlotOutput extends Slot {

        public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }

    public class SlotExp extends Slot {

        public SlotExp(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }
    }


}

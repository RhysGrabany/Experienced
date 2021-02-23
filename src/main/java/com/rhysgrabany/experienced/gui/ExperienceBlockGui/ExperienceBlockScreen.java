package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.gui.BaseContainer;
import com.rhysgrabany.experienced.gui.widgets.buttons.*;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;



public class ExperienceBlockScreen extends ContainerScreen<BaseContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");

    //region Coords for the graphical elements of the gui

    //region Arrow Coords
    // B/G
    final static int ARROW_BAR_XPOS = 48;
    final static int ARROW_BAR_YPOS = 34;

    // F/G
    final static int ARROW_BAR_TEX_U = 203;
    final static int ARROW_BAR_TEX_V = 28;

    // Width and Height B/G
    final static int ARROW_BAR_SPACING_X = 8;
    final static int ARROW_BAR_SPACING_Y = 13;

    //endregion

    //region Exp Bar Coords
    // B/G
    final static int EXP_BAR_XPOS = 8;
    final static int EXP_BAR_YPOS = 78;

    // F/G
    final static int EXP_BAR_TEX_U = 177;
    final static int EXP_BAR_TEX_V = 75;

    // Width and Height
    final static int EXP_BAR_SPACING_X = 21;
    final static int EXP_BAR_SPACING_Y = 72;
    //endregion

    //region Button Coords on GUI
    // Single Plus
    final static int SINGLE_PLUS_BUTTON_XPOS = 115;
    final static int SINGLE_PLUS_BUTTON_YPOS = 28;

    // Double Plus
    final static int DOUBLE_PLUS_BUTTON_XPOS = 129;
    final static int DOUBLE_PLUS_BUTTON_YPOS = 28;

    // Single Minus
    final static int SINGLE_MINUS_BUTTON_XPOS = 115;
    final static int SINGLE_MINUS_BUTTON_YPOS = 40;

    // Double Minus
    final static int DOUBLE_MINUS_BUTTON_XPOS = 129;
    final static int DOUBLE_MINUS_BUTTON_YPOS = 40;
    //endregion

    //region Button Spacings
    // Single Spacings
    final static int SINGLE_SPACING_X = 11;
    final static int SINGLE_SPACING_Y = 11;

    //Double Spacings
    final static int DOUBLE_SPACING_X = 17;
    final static int DOUBLE_SPACING_Y = 9;
    //endregion
    //endregion

    public ExperienceBlockScreen(BaseContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        xSize = 176;
        ySize = 166;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

//        final float LABEL_XPOS = 5;
//        final float FONT_Y_SPACING = 12;
//        final float EXP_BLOCK_LABEL_YPOS = ExperienceBlockContainer.TILE_INV_YPOS - FONT_Y_SPACING;
//        this.font.func_243248_b(matrixStack, this.title, LABEL_XPOS, EXP_BLOCK_LABEL_YPOS, Color.darkGray.getRGB());
//
//        final float PLAYER_INV_LABEL_YPOS = ExperienceBlockContainer.PLAYER_INV_YPOS - FONT_Y_SPACING;
//        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), LABEL_XPOS, PLAYER_INV_LABEL_YPOS, Color.darkGray.getRGB());


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f,1.0f,1.0f);

        int edgeSpacingX = (this.width - this.xSize)/2;
        int edgeSpacingY = (this.height - this.ySize)/2;

        // Drawing the main gui element
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);

        //Buttons adding to the gui
        this.addButton(new SingleMinusButton(guiLeft + SINGLE_MINUS_BUTTON_XPOS,  guiTop + SINGLE_MINUS_BUTTON_YPOS,
                new TranslationTextComponent(""), (press)->{}));
        this.addButton(new DoubleMinusButton(guiLeft + DOUBLE_MINUS_BUTTON_XPOS,  guiTop + DOUBLE_MINUS_BUTTON_YPOS,
                new TranslationTextComponent(""), (press)->{}));
        this.addButton(new SinglePlusButton(guiLeft + SINGLE_PLUS_BUTTON_XPOS,  guiTop + SINGLE_PLUS_BUTTON_YPOS,
                new TranslationTextComponent(""), (press)->{}));
        this.addButton(new DoublePlusButton(guiLeft + DOUBLE_PLUS_BUTTON_XPOS,  guiTop + DOUBLE_PLUS_BUTTON_YPOS,
                new TranslationTextComponent(""), (press)->{}));


        // Drawing the ExpBar
        this.blit(matrixStack, edgeSpacingX + EXP_BAR_XPOS, edgeSpacingY + EXP_BAR_YPOS - 17,
                EXP_BAR_TEX_U, EXP_BAR_TEX_V - 17, EXP_BAR_SPACING_X, EXP_BAR_SPACING_Y);

        // Drawing the Arrow
        this.blit(matrixStack, edgeSpacingX + ARROW_BAR_XPOS, edgeSpacingY + ARROW_BAR_YPOS,
                ARROW_BAR_TEX_U, ARROW_BAR_TEX_V, ARROW_BAR_SPACING_X, ARROW_BAR_SPACING_Y);

    }

}

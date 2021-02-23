package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.gui.BaseContainer;
import com.rhysgrabany.experienced.gui.SingleMinusButton;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;



public class ExperienceBlockScreen extends ContainerScreen<BaseContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");

    // Coords for the graphical elements of the gui
    // Arrow Coords
    // B/G
    final static int ARROW_BAR_XPOS = 199;
    final static int ARROW_BAR_YPOS = 4;


    // Exp Bar Coords
    // B/G
    final static int EXP_BAR_XPOS = 8;
    final static int EXP_BAR_YPOS = 78;

    // Width and Height
    final static int EXP_BAR_SPACING_X = 21;
    final static int EXP_BAR_SPACING_Y = 72;

    // F/G
    final static int EXP_BAR_TEX_U = 177;
    final static int EXP_BAR_TEX_V = 75;


    // Buttons
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
    final static int DOUBLE_MINUS_BUTTON_YPOS = 41;

    // Button Spacings
    final static int SINGLE_SPACING_X = 11;
    final static int SINGLE_SPACING_Y = 11;

    final static int DOUBLE_SPACING_X = 17;
    final static int DOUBLE_SPACING_Y = 9;



    public ExperienceBlockScreen(BaseContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        xSize = 176;
        ySize = 166;
    }

//    @Override
//    protected void init() {
//
//        super.init();
//
//        //this.buttons.clear();
//
//    }

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

        // Drawing the button for single minus
        // (x,y) this can be the (0,0) of the gui element, then the offsets to get the texture, then the width and height
//        this.blit(matrixStack, edgeSpacingX + SINGLE_MINUS_BUTTON_XPOS,  edgeSpacingY + SINGLE_MINUS_BUTTON_YPOS,
//                SINGLE_MINUS_BUTTON_XPOS, SINGLE_MINUS_BUTTON_YPOS,
//                SINGLE_SPACING_X, SINGLE_SPACING_Y);


        this.addButton(new SingleMinusButton(guiLeft + SINGLE_MINUS_BUTTON_XPOS,  guiTop + SINGLE_MINUS_BUTTON_YPOS,
                new TranslationTextComponent(""), (press)->{}));


        // Drawing the ExpBar
        this.blit(matrixStack, edgeSpacingX + EXP_BAR_XPOS, edgeSpacingY + EXP_BAR_YPOS - 17,
                EXP_BAR_TEX_U, EXP_BAR_TEX_V - 17, EXP_BAR_SPACING_X, EXP_BAR_SPACING_Y);




    }

}

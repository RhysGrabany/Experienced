package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.gui.BaseContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public class ExperienceBlockScreen extends ContainerScreen<BaseContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");

    // Coords for the graphical elements of the gui
    final static int ARROW_BAR_XPOS = 199;
    final static int ARROW_BAR_YPOS = 4;

    final static int EXP_BAR_XPOS = 177;
    final static int EXP_BAR_YPOS = 4;



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

        RenderSystem.color4f(1.0f, 1.0f,1.0f,1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        int edgeSpacingX = (this.width - this.xSize)/2;
        int edgeSpacingY = (this.height - this.ySize)/2;

        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);

    }
}

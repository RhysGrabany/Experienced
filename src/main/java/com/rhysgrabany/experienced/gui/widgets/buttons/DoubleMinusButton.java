package com.rhysgrabany.experienced.gui.widgets.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DoubleMinusButton extends Button {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");


    public DoubleMinusButton(int x, int y, ITextComponent title, IPressable pressedAction) {
        super(x, y, 19, 11, title, pressedAction);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //Coords to base button
        final int DOUBLE_MINUS_BUTTON_BASE_U = 229;
        final int DOUBLE_MINUS_BUTTON_BASE_V = 68;

        // Coords to the texture of hovered button texture
        final int DOUBLE_MINUS_BUTTON_HOV_U = 229;
        final int DOUBLE_MINUS_BUTTON_HOV_V = 17;

        // Coords to the texture of the selected button texture
        final int DOUBLE_MINUS_BUTTON_SEL_U = 229;
        final int DOUBLE_MINUS_BUTTON_SEL_V = 42;


        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f,1.0f,1.0f);

        this.blit(matrixStack, x, y,
                DOUBLE_MINUS_BUTTON_BASE_U, DOUBLE_MINUS_BUTTON_BASE_V,
                width, height);


        if (isHovered()){
            this.blit(matrixStack, x, y,
                    DOUBLE_MINUS_BUTTON_HOV_U, DOUBLE_MINUS_BUTTON_HOV_V,
                    width, height);
        }
    }


}

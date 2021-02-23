package com.rhysgrabany.experienced.gui.widgets.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SinglePlusButton extends Button {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");

    public SinglePlusButton(int x, int y, ITextComponent title, IPressable pressedAction) {
        super(x, y, 11, 11, title, pressedAction);
    }


    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        //Coords to base button
        final int SINGLE_PLUS_BUTTON_BASE_U = 216;
        final int SINGLE_PLUS_BUTTON_BASE_V = 55;

        // Coords to the texture of hovered button texture
        final int SINGLE_PLUS_BUTTON_HOV_U = 216;
        final int SINGLE_PLUS_BUTTON_HOV_V = 4;

        // Coords to the texture of the selected button texture
        final int SINGLE_PLUS_BUTTON_SEL_U = 216;
        final int SINGLE_PLUS_BUTTON_SEL_V = 29;


        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f,1.0f,1.0f);

        this.blit(matrixStack, x, y,
                SINGLE_PLUS_BUTTON_BASE_U, SINGLE_PLUS_BUTTON_BASE_V,
                width, height);


        if (isHovered()){
            this.blit(matrixStack, x, y,
                    SINGLE_PLUS_BUTTON_HOV_U, SINGLE_PLUS_BUTTON_HOV_V,
                    width, height);
        }

    }

    private static boolean inBounds(int x, int y, int xSize, int ySize, double mouseX, double mouseY) {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
}

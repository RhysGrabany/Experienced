package com.rhysgrabany.experienced.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public class SingleMinusButton extends Button {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");


    public SingleMinusButton(int x, int y, ITextComponent title, IPressable pressedAction) {
        super(x, y, 11, 11, title, pressedAction);

    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        //Coords to base button
        final int SINGLE_MINUS_BUTTON_BASE_U = 216;
        final int SINGLE_MINUS_BUTTON_BASE_V = 68;

        // Coords to the texture of hovered button texture
        final int SINGLE_MINUS_BUTTON_HOV_U = 216;
        final int SINGLE_MINUS_BUTTON_HOV_V = 17;

        // Coords to the texture of the selected button texture
        final int SINGLE_MINUS_BUTTON_SEL_U = 216;
        final int SINGLE_MINUS_BUTTON_SEL_V = 42;


        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f,1.0f,1.0f);

        this.blit(matrixStack, x, y,
                SINGLE_MINUS_BUTTON_BASE_U, SINGLE_MINUS_BUTTON_BASE_V,
                width, height);



        if (isHovered()){
            this.blit(matrixStack, x, y,
                    SINGLE_MINUS_BUTTON_HOV_U, SINGLE_MINUS_BUTTON_HOV_V,
                    width, height);
        }

    }

    private static boolean inBounds(int x, int y, int xSize, int ySize, double mouseX, double mouseY) {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }


}

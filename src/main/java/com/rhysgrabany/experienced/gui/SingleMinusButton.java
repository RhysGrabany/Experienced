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

    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MOD_ID,
            "textures/gui/experience_block_gui.png");

    public SingleMinusButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        int xSize = 176;
        int ySize = 166;

        final int SINGLE_MINUS_BUTTON_XPOS = 115;
        final int SINGLE_MINUS_BUTTON_YPOS = 40;

        Minecraft minecraft = Minecraft.getInstance();

        isHovered = inBounds(x, y, width, height, mouseX, mouseY);

        minecraft.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.enableDepthTest();
        RenderSystem.color4f(1.0f, 1.0f,1.0f,1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        int textureX = (this.width - xSize)/2 + SINGLE_MINUS_BUTTON_XPOS;
        int textureY = (this.height - ySize)/2 + SINGLE_MINUS_BUTTON_YPOS;

        int width = 11;
        int height = 11;

        this.blit(matrixStack, textureX, textureY, SINGLE_MINUS_BUTTON_XPOS, SINGLE_MINUS_BUTTON_YPOS, width, height);


        if(isHovered){
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.5f);
            this.blit(matrixStack, );
        }

        this.renderBg(matrixStack, minecraft, mouseX, mouseY);

    }

    private static boolean inBounds(int x, int y, int w, int h, double ox, double oy){
        return ox >= x && ox <= x + w && oy >= y && oy <= y + h;
    }


}

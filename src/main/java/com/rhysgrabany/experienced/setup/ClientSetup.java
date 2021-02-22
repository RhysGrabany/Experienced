package com.rhysgrabany.experienced.setup;

import com.rhysgrabany.experienced.ModContainers;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientSetup {

    public ClientSetup(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent e){
        e.enqueueWork(
                () -> ScreenManager.registerFactory(ModContainers.EXPERIENCE_BLOCK_CONTAINER.get(),
                        ExperienceBlockScreen::new));
    }


}

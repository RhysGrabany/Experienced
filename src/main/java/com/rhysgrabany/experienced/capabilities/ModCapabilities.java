package com.rhysgrabany.experienced.capabilities;

import com.rhysgrabany.experienced.capabilities.experience.ExperienceStorageHandler;
import com.rhysgrabany.experienced.capabilities.experience.IExperienceStorage;
import com.rhysgrabany.experienced.config.Constants;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {


    @CapabilityInject(IExperienceStorage.class)
    public static Capability<IExperienceStorage> EXPERIENCE_STORAGE_CAPABILITY = null;


    private ModCapabilities(){}

    public static void register(){

        ExperienceStorageHandler.register();

    }


}

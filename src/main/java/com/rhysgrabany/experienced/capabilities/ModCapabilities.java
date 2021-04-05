package com.rhysgrabany.experienced.capabilities;

import com.rhysgrabany.experienced.capabilities.experience.CapabilityExperience;
import com.rhysgrabany.experienced.capabilities.experience.IExperienceStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModCapabilities {


    @CapabilityInject(IExperienceStorage.class)
    public static Capability<IExperienceStorage> EXPERIENCE_STORAGE_CAPABILITY = null;





    private ModCapabilities(){}

    public static void register(){

        CapabilityExperience.register();

    }


}

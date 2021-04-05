package com.rhysgrabany.experienced.capabilities.experience;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityExperience {


    @CapabilityInject(IExperienceStorage.class)
    public static Capability<IExperienceStorage> EXPERIENCE = null;




    public static void register(){
        CapabilityManager.INSTANCE.register(IExperienceStorage.class, new Capability.IStorage<IExperienceStorage>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IExperienceStorage> capability, IExperienceStorage instance, Direction side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IExperienceStorage> capability, IExperienceStorage instance, Direction side, INBT nbt) {

            }
        }, () -> new ExperienceStorageProvider(1000));
    }





}

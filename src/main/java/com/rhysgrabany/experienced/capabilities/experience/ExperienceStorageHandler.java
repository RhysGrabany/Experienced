package com.rhysgrabany.experienced.capabilities.experience;

import com.google.common.base.Preconditions;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.Callable;


public class ExperienceStorageHandler {


    @SubscribeEvent
    public static void register(){
        CapabilityManager.INSTANCE.register(IExperienceStorage.class, new ExperienceStorage(), new ExperienceFactory());

    }

    private static class ExperienceStorage implements Capability.IStorage<IExperienceStorage>{
        @Override
        public INBT writeNBT(Capability<IExperienceStorage> capability, IExperienceStorage instance, Direction side) {
            return IntNBT.valueOf(instance.getExperienceStored());
        }
        @Override
        public void readNBT(Capability<IExperienceStorage> capability, IExperienceStorage instance, Direction side, INBT nbt) {
            if(!(instance instanceof IExperienceStorage)){
                throw new IllegalArgumentException("Can not deserialize");
            }
            ((ExperienceStorageProvider)instance).experience = ((IntNBT)nbt).getInt();
        }
    }

    private static class ExperienceFactory implements Callable<IExperienceStorage>{
        @Override
        public IExperienceStorage call() throws Exception {
            return new ExperienceStorageProvider(1000);
        }
    }

    private ExperienceStorageHandler(){}

}

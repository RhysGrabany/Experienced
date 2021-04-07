package com.rhysgrabany.experienced.capabilities.experience;

import com.rhysgrabany.experienced.capabilities.ModCapabilities;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderExpItems extends ExperienceStorageProvider implements ICapabilitySerializable<INBT> {

    private IExperienceStorage experienceItem = new ExperienceStorageProvider(1000);

    public CapabilityProviderExpItems(int capacity) {
        super(capacity);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if(cap == ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY){
            return (LazyOptional<T>)LazyOptional.of(() -> experienceItem);
        }
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY.writeNBT(experienceItem, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        ModCapabilities.EXPERIENCE_STORAGE_CAPABILITY.readNBT(experienceItem, null, nbt);
    }
}

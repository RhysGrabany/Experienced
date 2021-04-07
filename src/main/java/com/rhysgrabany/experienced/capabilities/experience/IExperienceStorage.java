package com.rhysgrabany.experienced.capabilities.experience;


// Modeled after EnergyStorage from Forge cause that's basically what I want for experience
public interface IExperienceStorage {

    void setExperience(int expAmount);

    // Adds experience to the storage. Returns quantity of experience that was accepted
    int receiveExperience(int maxReceive, boolean simulate);

    // Removes experience from the storage. Returns quantity of experience that was removed
    int extractExperience(int maxExtract, boolean simulate);

    // Returns the amount of experience currently stored
    int getExperienceStored();

    // Returns the max amount of experience that can be stored
    int getMaxExperienceStored();

    // Returns if this storage can have experience extracted
    // If this is false, then any calls to extractExperience will return 0
    boolean canExtract();

    // Used to determine if this storage can receive experience
    // If false, then any calls to receiveExperience will return 0
    boolean canReceive();



}

package com.rhysgrabany.experienced.capabilities.experience;

public class ExperienceStorage implements IExperienceStorage {


    protected int experience;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;


    public ExperienceStorage(int capacity){
        this(capacity, capacity, capacity, 0);
    }

    public ExperienceStorage(int capacity, int maxTransfer){
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public ExperienceStorage(int capacity, int maxReceive, int maxExtract){
        this(capacity, maxReceive, maxExtract, 0);
    }

    public ExperienceStorage(int capacity, int maxReceive, int maxExtract, int experience){
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.experience = Math.max(0, Math.min(capacity, experience));
    }

    @Override
    public int receiveExperience(int maxReceive, boolean simulate) {

        if(!canReceive()){
            return 0;
        }

        int experienceReceived = Math.min(capacity - experience, Math.min(this.maxReceive, maxReceive));

        if(!simulate){
            experience += experienceReceived;
        }

        return experienceReceived;
    }

    @Override
    public int extractExperience(int maxExtract, boolean simulate) {

        if(!canExtract()){
            return 0;
        }

        int experienceExtracted = Math.min(experience, Math.min(this.maxExtract, maxExtract));

        if(!simulate){
            experience -= experienceExtracted;
        }

        return experienceExtracted;
    }

    @Override
    public int getExperienceStored() {
        return experience;
    }

    @Override
    public int getMaxExperienceStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }
}

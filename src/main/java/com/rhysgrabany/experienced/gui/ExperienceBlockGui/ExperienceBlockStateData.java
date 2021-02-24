package com.rhysgrabany.experienced.gui.ExperienceBlockGui;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class ExperienceBlockStateData implements IIntArray {

    public int expDrainElapsed;
    public int expDrainToComplete;

    public int expDrainTimeInitialValue;
    public int expDrainTimeRemaining;


    public void putIntoNBT(CompoundNBT nbt) {

        nbt.putInt("ExpDrainElapsed", expDrainElapsed);
        nbt.putInt("ExpDrainToComplete", expDrainToComplete);
        nbt.putInt("ExpDrainTimeInitialValue", expDrainTimeInitialValue);
        nbt.putInt("ExpDrainTimeRemaining", expDrainTimeRemaining);

    }

    public void readFromNBT(CompoundNBT nbt) {

        expDrainElapsed = nbt.getInt("ExpDrainElapsed");
        expDrainToComplete = nbt.getInt("ExpDrainToComplete");
        expDrainTimeInitialValue = nbt.getInt("ExpDrainTimeInitialValue");
        expDrainTimeRemaining = nbt.getInt("ExpDrainTimeRemaining");
    }

    // Indexing

    private final int DRAINTIME_INDEX = 0;
    private final int DRAIN_TO_COMPLETION_INDEX = 1;
    private final int DRAINTIME_INITIAL_VALUE_INDEX = 2;
    private final int DRAINTIME_REMAINING_INDEX = 3;


    @Override
    public int get(int index) {
        validateIndex(index);

        switch (index) {
            case DRAINTIME_INDEX:
                return expDrainElapsed;
            case DRAIN_TO_COMPLETION_INDEX:
                return expDrainToComplete;
            case DRAINTIME_INITIAL_VALUE_INDEX:
                return expDrainTimeInitialValue;
            default:
                return expDrainTimeRemaining;
        }

    }

    @Override
    public void set(int index, int value) {

        validateIndex(index);

        switch (index){
            case DRAINTIME_INDEX:
                expDrainElapsed = value;
                break;
            case DRAIN_TO_COMPLETION_INDEX:
                expDrainToComplete = value;
                break;
            case DRAINTIME_INITIAL_VALUE_INDEX:
                expDrainTimeInitialValue = value;
            default:
                expDrainTimeRemaining = value;
                break;
        }

    }

    @Override
    public int size() {
        return 0;
    }


    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }


}

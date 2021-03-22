package com.rhysgrabany.experienced.recipe.impl;

import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public interface InputIngredient<T> extends Predicate<T> {


    boolean testType(@Nonnull T type);

    T getMatchingInstance(T type);

    default long getNeededAmount(T type){
        return testType(type) ? 1 : 0;
    }

    void write(PacketBuffer buff);

    @Nonnull
    JsonElement serialize();


}

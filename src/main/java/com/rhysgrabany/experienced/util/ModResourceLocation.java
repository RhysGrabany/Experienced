package com.rhysgrabany.experienced.util;

import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.util.ResourceLocation;

public class ModResourceLocation extends ResourceLocation {

    public ModResourceLocation(String resourceName) {
        super(addModNamespace(resourceName));
    }

    private static String addModNamespace(String resource){
        return Constants.MOD_ID + ":" + resource;
    }


}

package com.rhysgrabany.experienced.data;

import com.rhysgrabany.experienced.config.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {


    private DataGenerators(){}


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){

        DataGenerator generator = event.getGenerator();

        

    }


}

package com.rhysgrabany.experienced;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.network.NetworkHandler;
import com.rhysgrabany.experienced.recipe.ExperiencedRecipeType;
import com.rhysgrabany.experienced.setup.ClientSetup;
import com.rhysgrabany.experienced.setup.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Constants.MOD_ID)
public class Experienced
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Experienced() {

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);
        DeferredWorkQueue.runLater(NetworkHandler::init);

        Registration.register();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

//    private void registerRecipeType(RegistryEvent.Register<IRecipeSerializer<?>> e){
//        ExperiencedRecipeType.re
//    }


    public static ModelResourceLocation getId(String path){
        if(path.contains(":")){
            throw new IllegalArgumentException("Path contains namespace");
        }
        return new ModelResourceLocation(path);
    }


}

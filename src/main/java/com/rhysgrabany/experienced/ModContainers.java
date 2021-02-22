package com.rhysgrabany.experienced;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContainer;
import com.rhysgrabany.experienced.setup.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);

    public static final RegistryObject<ContainerType<ExperienceBlockContainer>> EXPERIENCE_BLOCK_CONTAINER;

    static{

        EXPERIENCE_BLOCK_CONTAINER = Registration.CONTAINERS.register("experience_block_container",
                () -> IForgeContainerType.create(ExperienceBlockContainer::createContainerClientSide));

    }


    public static void register(){
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }



}

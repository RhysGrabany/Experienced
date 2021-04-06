package com.rhysgrabany.experienced;

import com.rhysgrabany.experienced.config.Constants;
import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContainer;
import com.rhysgrabany.experienced.setup.Registration;
import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import com.rhysgrabany.experienced.tile.data.ContainerTileFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainers {

    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);

    public static final RegistryObject<ContainerType<ExperienceBlockContainer>> EXPERIENCE_BLOCK_CONTAINER;

    static {
        EXPERIENCE_BLOCK_CONTAINER = CONTAINERS.register("experience_block_container",
                () -> new ContainerType<ExperienceBlockContainer>(new IContainerFactory<ExperienceBlockContainer>() {
                    @Override
                    public ExperienceBlockContainer create(int windowId, PlayerInventory inv, PacketBuffer data) {
                        if (data != null) {
                            BlockPos tePos = new BlockPos(data.readBlockPos());
                            TileEntity te = inv.player.world.getTileEntity(tePos);

                            if (te != null && te instanceof ExperienceBlockTile) {
                                return ExperienceBlockContainer.createContainerClientSide(windowId, inv, (ExperienceBlockTile)te);
                            }
                        }
                        return new ExperienceBlockContainer(windowId, inv);
                    }
                }));
    }

    public static void register() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}

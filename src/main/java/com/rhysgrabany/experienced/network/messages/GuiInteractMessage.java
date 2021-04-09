package com.rhysgrabany.experienced.network.messages;

import com.rhysgrabany.experienced.gui.ExperienceBlockGui.ExperienceBlockContainer;
import com.rhysgrabany.experienced.tile.BaseTile;
import com.sun.xml.internal.rngom.parse.host.Base;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.util.TriConsumer;

public class GuiInteractMessage {




    public enum GuiInteractionItem{
        ;
        private final TriConsumer<BaseTile, PlayerEntity, ItemStack> consumer;

        GuiInteractionItem(TriConsumer<BaseTile, PlayerEntity, ItemStack> consumer){
            this.consumer = consumer;
        }

        public void consume(BaseTile tile, PlayerEntity player, ItemStack stack){
            consumer.accept(tile, player, stack);
        }
    }

    public enum GuiInteraction{

        EXPERIENCE_BLOCK_SINGLE_PLUS((tile, player, extra) ->{
            if(player.openContainer instanceof ExperienceBlockContainer){
                tile.writeUpdate(extra)
            }
        });


        private final TriConsumer<BaseTile, PlayerEntity, Integer> consumer;

        GuiInteraction(TriConsumer<BaseTile, PlayerEntity, Integer> consumer){
            this.consumer = consumer;
        }

        public void consumer(BaseTile tile, PlayerEntity player, int extra){
            consumer.accept(tile, player, extra);
        }


    }





}

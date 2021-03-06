package com.rhysgrabany.experienced.block;

import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ExperienceBlock extends Block {

    // The tiers of the experience block, this makes it easy to register the blocks and all that sort of stuff
    public Tier BLOCK_TIER;

    public enum Tier {
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large"),
        CREATIVE("creative");

        final String name;

        Tier(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }


    public ExperienceBlock(Tier tier) {
        super(AbstractBlock
                .Properties
                .create(Material.ROCK)
                .harvestLevel(2));
        BLOCK_TIER = tier;
    }

    //region Gui Functions

    //region Tile Entity

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ExperienceBlockTile(BLOCK_TIER);
    }

    //endregion

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        return tile instanceof INamedContainerProvider ? (INamedContainerProvider) tile : null;
    }

    //endregion

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if(worldIn.isRemote){
            return ActionResultType.SUCCESS;
        }

        // If the selected block has no container then one will be made for it
        INamedContainerProvider nmContainer = this.getContainer(state, worldIn, pos);
        if(nmContainer != null){

            TileEntity tile = worldIn.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider)tile, new Consumer<PacketBuffer>() {
                @Override
                public void accept(PacketBuffer packetBuffer) {
                    packetBuffer.writeBlockPos(pos);
                }
            });

        }

        return ActionResultType.SUCCESS;

    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {


        if(state.getBlock() != newState.getBlock()){
            TileEntity tile = worldIn.getTileEntity(pos);

            if(tile instanceof ExperienceBlockTile){
                ExperienceBlockTile tileEntity = (ExperienceBlockTile) tile;
                tileEntity.dropContents(worldIn, pos);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);

        }

    }

    //region Helper Methods for Block

    // Method used to initialize the max amount of exp a certain block can hold
    // For now; Small = 30, Medium = 60, Large = 100, Creative = MAX_VALUE, and default is 0 cause that might not happen


    //endregion

}

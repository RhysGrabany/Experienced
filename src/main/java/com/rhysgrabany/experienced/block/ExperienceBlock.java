package com.rhysgrabany.experienced.block;

import com.rhysgrabany.experienced.tile.ExperienceBlockTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ExperienceBlock extends Block {

    // The tiers of the experience block, this makes it easy to register the blocks and all that sort of stuff
    public static Tier BLOCK_TIER;

    // Max Exp a block can hold
    private final int MAX_EXP;

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
        MAX_EXP = initMaxValue(tier);



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
        return new ExperienceBlockTile();
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

        INamedContainerProvider nmContainer = this.getContainer(state, worldIn, pos);
        if(nmContainer != null){

            TileEntity tile = worldIn.getTileEntity(pos);
            NetworkHooks.openGui((ServerPlayerEntity) player, nmContainer, (packetBuffer -> {}));

        }

        return ActionResultType.SUCCESS;


    }


    //region Helper Methods for Block

    // Method used to initialize the max amount of exp a certain block can hold
    // For now; Small = 30, Medium = 60, Large = 100, Creative = MAX_VALUE, and default is 0 cause that might not happen
    private static int initMaxValue(Tier tier){
        switch(tier){
            case SMALL:
                return 1395;
            case MEDIUM:
                return 8670;
            case LARGE:
                return 30970;
            case CREATIVE:
                return Integer.MAX_VALUE;
            default:
                return 0;
        }
    }

    //endregion

}

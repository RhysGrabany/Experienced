package com.rhysgrabany.experienced.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BaseBlockItem extends BlockItem {

    private final Block block;

    public BaseBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.block = blockIn;
    }


}

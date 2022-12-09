package me.mathrandom7910.ghoulish.client.util;

import me.mathrandom7910.ghoulish.client.misc.MCInst;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockUtil implements MCInst {
    public static BlockState getState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }
}

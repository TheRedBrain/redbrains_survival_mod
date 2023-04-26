package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.block.BlockMixinDuck;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin implements BlockMixinDuck {

    public boolean hasPyramid(World world, BlockPos pos, Block pyramidMaterial) {
        int i = 0;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if(!(world.getBlockState(new BlockPos(x, y-1, z)).isOf(pyramidMaterial))) {
            return false;
        }

        for(int j = 1; j <= 4; i = j++) {
            int k = y - j;
            if (k < world.getBottomY()) {
                break;
            }

            boolean bl = true;

            for(int l = x - j; l <= x + j && bl; ++l) {
                for(int m = z - j; m <= z + j; ++m) {
                    if (!world.getBlockState(new BlockPos(l, k, m)).isOf(pyramidMaterial)) {
                        bl = false;
                        break;
                    }
                }
            }

            if (!bl) {
                break;
            }
        }

        return i == 4;
    }
}

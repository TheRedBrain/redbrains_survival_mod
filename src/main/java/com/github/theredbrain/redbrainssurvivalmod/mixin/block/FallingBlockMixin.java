package com.github.theredbrain.redbrainssurvivalmod.mixin.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block {

    @Shadow
    protected int getFallDelay() {
        throw new AssertionError();
    }

    public FallingBlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * Called when an entity steps on this block.
     *
     * <p>This method is called on both the logical client and logical server, so take caution
     * when overriding this method. The logical side can be checked using {@link
     * World#isClient}.
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof PlayerEntity) {
            world.scheduleBlockTick(pos, this, this.getFallDelay());
        }
    }

    @Inject(method = "canFallThrough", at = @At("RETURN"), cancellable = true)
    private static void canFallThroughMoreBlocks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || state.isIn(Tags.DESTROYED_BY_FALLING_BLOCKS));
    }
}

package com.github.theredbrain.redbrainssurvivalmod.block;

import com.github.theredbrain.redbrainssurvivalmod.registry.EntitiesRegistry;
import com.github.theredbrain.redbrainssurvivalmod.screen.SoulforgeScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SoulforgeBlock extends BlockWithEntity implements ForgeShape {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final Text TITLE = Text.translatable("container.crafting");

    public SoulforgeBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return EntitiesRegistry.SOULFORGE_ENTITY.instantiate(pos, state);
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
//        SoulforgeBlockEntity soulforgeBlockEntity;
//        BlockEntity blockEntity = world.getBlockEntity(pos);
//        if (blockEntity != null && blockEntity.getType() == EntitiesRegistry.SOULFORGE_ENTITY) {
//            soulforgeBlockEntity = (SoulforgeBlockEntity) blockEntity;
//            return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new SoulforgeScreenHandler(syncId, inventory, soulforgeBlockEntity.getInventory(), ScreenHandlerContext.create(world, pos)), TITLE);
//        }
//        // should never happen
//        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new SoulforgeScreenHandler(syncId, inventory, DefaultedList.ofSize(16, ItemStack.EMPTY), ScreenHandlerContext.create(world, pos)), TITLE);
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new SoulforgeScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction facing = state.get(FACING);

        return switch (facing) {
            case EAST -> COMPLETE_SHAPE_EAST;
            case SOUTH -> COMPLETE_SHAPE_SOUTH;
            case WEST -> COMPLETE_SHAPE_WEST;
            default -> COMPLETE_SHAPE_NORTH;
        };
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().rotateYCounterclockwise());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
//        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE); // TODO stats
        return ActionResult.CONSUME;
    }

}

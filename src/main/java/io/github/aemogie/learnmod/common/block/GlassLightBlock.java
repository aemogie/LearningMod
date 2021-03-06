package io.github.aemogie.learnmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import static net.minecraft.client.renderer.RenderType.translucent;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class GlassLightBlock extends ModBlock {
	
	private static final BooleanProperty LIT = BlockStateProperties.LIT;
	private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	
	private static final float HARDNESS = 1.5f;
	private static final float RESISTANCE = 6.0f;
	
	public GlassLightBlock() {
		super(Properties.of(Material.GLASS)
				.sound(SoundType.GLASS)
				.noOcclusion()
				.strength(HARDNESS, RESISTANCE)
				.harvestTool(ToolType.PICKAXE),
				translucent());
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(POWERED, false));
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
		builder.add(POWERED);
	}
	
	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
		toggleLit(state, world, pos);
		return ActionResultType.SUCCESS;
	}
	
	public void toggleLit(BlockState state, World worldIn, BlockPos pos) {
		
		if (state.getValue(POWERED).equals(false)) {
			
			if (state.getValue(LIT).equals(false)) {
				worldIn.setBlock(pos,
						this.getStateDefinition().any()
								.setValue(LIT, true)
								.setValue(POWERED, false),
						3
				);
			} else if (state.getValue(LIT).equals(true)) {
				worldIn.setBlock(pos,
						this.getStateDefinition().any()
								.setValue(LIT, false)
								.setValue(POWERED, false),
						3
				);
			}
			
		} else if (state.getValue(POWERED).equals(true)) {
			worldIn.setBlock(pos, this.getStateDefinition().any().setValue(LIT, true),  3);
		}
		
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		
		if (state.getValue(LIT)) {
			return 15;
		} else {
			return 0;
		}
		
	}
	
	@Override
	public float getDestroyProgress(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
		if (state.getValue(LIT)) {
			
			return super.getDestroyProgress(state, player, worldIn, pos) / 5f;
			
		} else {
			return super.getDestroyProgress(state, player, worldIn, pos);
		}
	}
}

package twilightforest.structures.stronghold;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.List;
import java.util.Random;

public class StrongholdAccessChamberComponent extends StructureTFStrongholdComponent {

	public StrongholdAccessChamberComponent(ServerLevel level, CompoundTag nbt) {
		super(StrongholdPieces.TFSAC, nbt);
	}

	public StrongholdAccessChamberComponent(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(StrongholdPieces.TFSAC, feature, i, facing, x, y, z);
	}

	@Override
	public BoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return BoundingBox.orientBox(x, y, z, -4, 1, 0, 9, 5, 9, facing);
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current Location of StructGen
	 */
	@Override
	public void addChildren(StructurePiece parent, StructurePieceAccessor list, Random random) {
		super.addChildren(parent, list, random);

		// make a random component in each direction
		addNewUpperComponent(parent, list, random, Rotation.NONE, 4, 1, 9);
		addNewUpperComponent(parent, list, random, Rotation.CLOCKWISE_90, -1, 1, 4);
		addNewUpperComponent(parent, list, random, Rotation.CLOCKWISE_180, 4, 1, -1);
		addNewUpperComponent(parent, list, random, Rotation.COUNTERCLOCKWISE_90, 9, 1, 4);

	}

	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator, Random rand, BoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		generateBox(world, sbb, 0, 0, 0, 8, 4, 8, true, rand, deco.randomBlocks);

		// doors
		placeSmallDoorwayAt(world, 0, 4, 1, 8, sbb);
		placeSmallDoorwayAt(world, 1, 0, 1, 4, sbb);
		placeSmallDoorwayAt(world, 2, 4, 1, 0, sbb);
		placeSmallDoorwayAt(world, 3, 8, 1, 4, sbb);

		// shaft down
		final BlockState defaultState = Blocks.MOSSY_STONE_BRICKS.defaultBlockState();
		this.generateBox(world, sbb, 2, -2, 2, 6, 0, 6, defaultState, AIR, false);

		this.generateAirBox(world, sbb, 3, -2, 3, 5, 2, 5);

		// stairs surrounding shaft
		this.generateBox(world, sbb, 2, 0, 3, 2, 0, 6, getStairState(deco.stairState, Rotation.CLOCKWISE_180.rotate(Direction.WEST), false), AIR, false);
		this.generateBox(world, sbb, 6, 0, 2, 6, 0, 6, getStairState(deco.stairState, Rotation.NONE.rotate(Direction.WEST), false), AIR, false);
		this.generateBox(world, sbb, 3, 0, 2, 5, 0, 2, getStairState(deco.stairState, Rotation.COUNTERCLOCKWISE_90.rotate(Direction.WEST), false), AIR, false);
		this.generateBox(world, sbb, 3, 0, 6, 5, 0, 6, getStairState(deco.stairState, Rotation.CLOCKWISE_90.rotate(Direction.WEST), false), AIR, false);

		// pillar
		this.placeBlock(world, deco.pillarState, 2, 0, 2, sbb);

		// pedestal
		this.placeBlock(world, TFBlocks.trophy_pedestal.get().defaultBlockState(), 2, 1, 2, sbb);

		// block point
		this.generateBox(world, sbb, 2, -1, 2, 6, -1, 6, TFBlocks.stronghold_shield.get().defaultBlockState(), AIR, false);

		return true;
	}

	@Override
	public boolean isComponentProtected() {
		return false;
	}
}
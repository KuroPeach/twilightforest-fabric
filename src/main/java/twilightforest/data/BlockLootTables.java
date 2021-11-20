package twilightforest.data;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import com.google.common.collect.Sets;
import twilightforest.block.*;
import twilightforest.enums.HollowLogVariants;
import twilightforest.item.TFItems;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class BlockLootTables extends net.minecraft.data.loot.BlockLoot {
	private final Set<Block> knownBlocks = new HashSet<>();
	// [VanillaCopy] super
	private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

	@Override
	public void add(Block block, LootTable.Builder builder) {
		super.add(block, builder);
		knownBlocks.add(block);
	}

	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_124179_) {
		this.addTables();
		Set<ResourceLocation> set = Sets.newHashSet();

		for(Block block : getKnownBlocks()) {
			ResourceLocation resourcelocation = block.getLootTable();
			if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
				LootTable.Builder loottable$builder = this.map.remove(resourcelocation);
				if (loottable$builder == null) {
					throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, Registry.BLOCK.getKey(block)));
				}

				p_124179_.accept(resourcelocation, loottable$builder);
			}
		}

		if (!this.map.isEmpty()) {
			throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
		}
	}

	//@Override
	protected void addTables() {
		registerEmpty(TFBlocks.EXPERIMENT_115.get());
		dropSelf(TFBlocks.TOWERWOOD.get());
		dropSelf(TFBlocks.ENCASED_TOWERWOOD.get());
		dropSelf(TFBlocks.CRACKED_TOWERWOOD.get());
		dropSelf(TFBlocks.MOSSY_TOWERWOOD.get());
		registerEmpty(TFBlocks.ANTIBUILDER.get());
		dropSelf(TFBlocks.CARMINITE_BUILDER.get());
		dropSelf(TFBlocks.GHAST_TRAP.get());
		dropSelf(TFBlocks.CARMINITE_REACTOR.get());
		dropSelf(TFBlocks.REAPPEARING_BLOCK.get());
		dropSelf(TFBlocks.VANISHING_BLOCK.get());
		dropSelf(TFBlocks.LOCKED_VANISHING_BLOCK.get());
		dropSelf(TFBlocks.FIREFLY.get());
		dropSelf(TFBlocks.CICADA.get());
		dropSelf(TFBlocks.MOONWORM.get());
		dropSelf(TFBlocks.TROPHY_PEDESTAL.get());
		//registerDropSelfLootTable(TFBlocks.TERRORCOTTA_CIRCLE.get());
		//registerDropSelfLootTable(TFBlocks.TERRORCOTTA_DIAGONAL.get());
		dropSelf(TFBlocks.AURORA_BLOCK.get());
		dropSelf(TFBlocks.AURORA_PILLAR.get());
		add(TFBlocks.AURORA_SLAB.get(), createSlabItemTable(TFBlocks.AURORA_SLAB.get()));
		dropWhenSilkTouch(TFBlocks.AURORALIZED_GLASS.get());
		dropSelf(TFBlocks.UNDERBRICK.get());
		dropSelf(TFBlocks.CRACKED_UNDERBRICK.get());
		dropSelf(TFBlocks.MOSSY_UNDERBRICK.get());
		dropSelf(TFBlocks.UNDERBRICK_FLOOR.get());
		dropSelf(TFBlocks.THORN_ROSE.get());
		add(TFBlocks.THORN_LEAVES.get(), silkAndStick(TFBlocks.THORN_LEAVES.get(), Items.STICK, RARE_SAPLING_DROP_RATES));
		add(TFBlocks.BEANSTALK_LEAVES.get(), silkAndStick(TFBlocks.BEANSTALK_LEAVES.get(), TFItems.MAGIC_BEANS.get(), RARE_SAPLING_DROP_RATES));
		dropSelf(TFBlocks.DEADROCK.get());
		dropSelf(TFBlocks.CRACKED_DEADROCK.get());
		dropSelf(TFBlocks.WEATHERED_DEADROCK.get());
		dropSelf(TFBlocks.TROLLSTEINN.get());
		dropWhenSilkTouch(TFBlocks.WISPY_CLOUD.get());
		dropSelf(TFBlocks.FLUFFY_CLOUD.get());
		dropSelf(TFBlocks.GIANT_COBBLESTONE.get());
		dropSelf(TFBlocks.GIANT_LOG.get());
		dropSelf(TFBlocks.GIANT_LEAVES.get());
		dropSelf(TFBlocks.GIANT_OBSIDIAN.get());
		add(TFBlocks.UBEROUS_SOIL.get(), createSingleItemTable(Blocks.DIRT));
		dropSelf(TFBlocks.HUGE_STALK.get());
		add(TFBlocks.HUGE_MUSHGLOOM.get(), createMushroomBlockDrop(TFBlocks.HUGE_MUSHGLOOM.get(), TFBlocks.MUSHGLOOM.get()));
		add(TFBlocks.HUGE_MUSHGLOOM_STEM.get(), createMushroomBlockDrop(TFBlocks.HUGE_MUSHGLOOM_STEM.get(), TFBlocks.MUSHGLOOM.get()));
		add(TFBlocks.TROLLVIDR.get(), createShearsOnlyDrop(TFBlocks.TROLLVIDR.get()));
		add(TFBlocks.UNRIPE_TROLLBER.get(), createShearsOnlyDrop(TFBlocks.UNRIPE_TROLLBER.get()));
		add(TFBlocks.TROLLBER.get(), createShearsDispatchTable(TFBlocks.TROLLBER.get(), LootItem.lootTableItem(TFItems.TORCHBERRIES.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
		dropSelf(TFBlocks.HUGE_LILY_PAD.get());
		dropSelf(TFBlocks.HUGE_WATER_LILY.get());
		dropSelf(TFBlocks.CASTLE_BRICK.get());
		dropSelf(TFBlocks.WORN_CASTLE_BRICK.get());
		dropSelf(TFBlocks.CRACKED_CASTLE_BRICK.get());
		dropSelf(TFBlocks.MOSSY_CASTLE_BRICK.get());
		dropSelf(TFBlocks.THICK_CASTLE_BRICK.get());
		dropSelf(TFBlocks.CASTLE_ROOF_TILE.get());
		dropSelf(TFBlocks.ENCASED_CASTLE_BRICK_PILLAR.get());
		dropSelf(TFBlocks.ENCASED_CASTLE_BRICK_TILE.get());
		dropSelf(TFBlocks.BOLD_CASTLE_BRICK_PILLAR.get());
		dropSelf(TFBlocks.BOLD_CASTLE_BRICK_TILE.get());
		dropSelf(TFBlocks.CASTLE_BRICK_STAIRS.get());
		dropSelf(TFBlocks.WORN_CASTLE_BRICK_STAIRS.get());
		dropSelf(TFBlocks.CRACKED_CASTLE_BRICK_STAIRS.get());
		dropSelf(TFBlocks.MOSSY_CASTLE_BRICK_STAIRS.get());
		dropSelf(TFBlocks.ENCASED_CASTLE_BRICK_STAIRS.get());
		dropSelf(TFBlocks.BOLD_CASTLE_BRICK_STAIRS.get());
		dropSelf(TFBlocks.VIOLET_CASTLE_RUNE_BRICK.get());
		dropSelf(TFBlocks.YELLOW_CASTLE_RUNE_BRICK.get());
		dropSelf(TFBlocks.PINK_CASTLE_RUNE_BRICK.get());
		dropSelf(TFBlocks.BLUE_CASTLE_RUNE_BRICK.get());
		dropSelf(TFBlocks.CINDER_FURNACE.get());
		dropSelf(TFBlocks.CINDER_LOG.get());
		dropSelf(TFBlocks.CINDER_WOOD.get());
		dropSelf(TFBlocks.VIOLET_CASTLE_DOOR.get());
		dropSelf(TFBlocks.YELLOW_CASTLE_DOOR.get());
		dropSelf(TFBlocks.PINK_CASTLE_DOOR.get());
		dropSelf(TFBlocks.BLUE_CASTLE_DOOR.get());
		dropSelf(TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get());
		dropSelf(TFBlocks.NAGA_COURTYARD_MINIATURE_STRUCTURE.get());
		dropSelf(TFBlocks.LICH_TOWER_MINIATURE_STRUCTURE.get());
		dropSelf(TFBlocks.KNIGHTMETAL_BLOCK.get());
		dropSelf(TFBlocks.IRONWOOD_BLOCK.get());
		dropSelf(TFBlocks.FIERY_BLOCK.get());
		dropSelf(TFBlocks.STEELEAF_BLOCK.get());
		dropSelf(TFBlocks.ARCTIC_FUR_BLOCK.get());
		dropSelf(TFBlocks.CARMINITE_BLOCK.get());
		dropSelf(TFBlocks.MAZESTONE.get());
		dropSelf(TFBlocks.MAZESTONE_BRICK.get());
		dropSelf(TFBlocks.CUT_MAZESTONE.get());
		dropSelf(TFBlocks.DECORATIVE_MAZESTONE.get());
		dropSelf(TFBlocks.CRACKED_MAZESTONE.get());
		dropSelf(TFBlocks.MOSSY_MAZESTONE.get());
		dropSelf(TFBlocks.MAZESTONE_MOSAIC.get());
		dropSelf(TFBlocks.MAZESTONE_BORDER.get());
		dropWhenSilkTouch(TFBlocks.HEDGE.get());
		add(TFBlocks.ROOT_BLOCK.get(), createSingleItemTableWithSilkTouch(TFBlocks.ROOT_BLOCK.get(), Items.STICK, UniformGenerator.between(3, 5)));
		add(TFBlocks.LIVEROOT_BLOCK.get(), createSilkTouchDispatchTable(TFBlocks.LIVEROOT_BLOCK.get(), applyExplosionCondition(TFBlocks.LIVEROOT_BLOCK.get(), LootItem.lootTableItem(TFItems.LIVEROOT.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
		add(TFBlocks.MANGROVE_ROOT.get(), createSingleItemTableWithSilkTouch(TFBlocks.MANGROVE_ROOT.get(), Items.STICK, UniformGenerator.between(3, 5)));
		dropSelf(TFBlocks.UNCRAFTING_TABLE.get());
		dropSelf(TFBlocks.FIREFLY_JAR.get());
		add(TFBlocks.FIREFLY_SPAWNER.get(), particleSpawner());
		dropSelf(TFBlocks.CICADA_JAR.get());
		add(TFBlocks.MOSS_PATCH.get(), createShearsOnlyDrop(TFBlocks.MOSS_PATCH.get()));
		add(TFBlocks.MAYAPPLE.get(), createShearsOnlyDrop(TFBlocks.MAYAPPLE.get()));
		add(TFBlocks.CLOVER_PATCH.get(), createShearsOnlyDrop(TFBlocks.CLOVER_PATCH.get()));
		add(TFBlocks.FIDDLEHEAD.get(), createShearsOnlyDrop(TFBlocks.FIDDLEHEAD.get()));
		dropSelf(TFBlocks.MUSHGLOOM.get());
		add(TFBlocks.TORCHBERRY_PLANT.get(), torchberryPlant(TFBlocks.TORCHBERRY_PLANT.get()));
		add(TFBlocks.ROOT_STRAND.get(), block -> createShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
		add(TFBlocks.FALLEN_LEAVES.get(), createShearsOnlyDrop(TFBlocks.FALLEN_LEAVES.get()));
		dropSelf(TFBlocks.SMOKER.get());
		dropSelf(TFBlocks.ENCASED_SMOKER.get());
		dropSelf(TFBlocks.FIRE_JET.get());
		dropSelf(TFBlocks.ENCASED_FIRE_JET.get());
		dropSelf(TFBlocks.NAGASTONE_HEAD.get());
		dropSelf(TFBlocks.NAGASTONE.get());
		dropSelf(TFBlocks.SPIRAL_BRICKS.get());
		dropSelf(TFBlocks.NAGASTONE_PILLAR.get());
		dropSelf(TFBlocks.MOSSY_NAGASTONE_PILLAR.get());
		dropSelf(TFBlocks.CRACKED_NAGASTONE_PILLAR.get());
		dropSelf(TFBlocks.ETCHED_NAGASTONE.get());
		dropSelf(TFBlocks.MOSSY_ETCHED_NAGASTONE.get());
		dropSelf(TFBlocks.CRACKED_ETCHED_NAGASTONE.get());
		dropSelf(TFBlocks.NAGASTONE_STAIRS_LEFT.get());
		dropSelf(TFBlocks.NAGASTONE_STAIRS_RIGHT.get());
		dropSelf(TFBlocks.MOSSY_NAGASTONE_STAIRS_LEFT.get());
		dropSelf(TFBlocks.MOSSY_NAGASTONE_STAIRS_RIGHT.get());
		dropSelf(TFBlocks.CRACKED_NAGASTONE_STAIRS_LEFT.get());
		dropSelf(TFBlocks.CRACKED_NAGASTONE_STAIRS_RIGHT.get());
		add(TFBlocks.NAGA_TROPHY.get(), createSingleItemTable(TFBlocks.NAGA_TROPHY.get().asItem()));
		add(TFBlocks.NAGA_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.NAGA_TROPHY.get().asItem()));
		add(TFBlocks.LICH_TROPHY.get(), createSingleItemTable(TFBlocks.LICH_TROPHY.get().asItem()));
		add(TFBlocks.LICH_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.LICH_TROPHY.get().asItem()));
		add(TFBlocks.MINOSHROOM_TROPHY.get(), createSingleItemTable(TFBlocks.MINOSHROOM_TROPHY.get().asItem()));
		add(TFBlocks.MINOSHROOM_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.MINOSHROOM_TROPHY.get().asItem()));
		add(TFBlocks.HYDRA_TROPHY.get(), createSingleItemTable(TFBlocks.HYDRA_TROPHY.get().asItem()));
		add(TFBlocks.HYDRA_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.HYDRA_TROPHY.get().asItem()));
		add(TFBlocks.KNIGHT_PHANTOM_TROPHY.get(), createSingleItemTable(TFBlocks.KNIGHT_PHANTOM_TROPHY.get().asItem()));
		add(TFBlocks.KNIGHT_PHANTOM_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.KNIGHT_PHANTOM_TROPHY.get().asItem()));
		add(TFBlocks.UR_GHAST_TROPHY.get(), createSingleItemTable(TFBlocks.UR_GHAST_TROPHY.get().asItem()));
		add(TFBlocks.UR_GHAST_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.UR_GHAST_TROPHY.get().asItem()));
		add(TFBlocks.ALPHA_YETI_TROPHY.get(), createSingleItemTable(TFBlocks.ALPHA_YETI_TROPHY.get().asItem()));
		add(TFBlocks.ALPHA_YETI_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.ALPHA_YETI_TROPHY.get().asItem()));
		add(TFBlocks.SNOW_QUEEN_TROPHY.get(), createSingleItemTable(TFBlocks.SNOW_QUEEN_TROPHY.get().asItem()));
		add(TFBlocks.SNOW_QUEEN_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.SNOW_QUEEN_TROPHY.get().asItem()));
		add(TFBlocks.QUEST_RAM_TROPHY.get(), createSingleItemTable(TFBlocks.QUEST_RAM_TROPHY.get().asItem()));
		add(TFBlocks.QUEST_RAM_WALL_TROPHY.get(), createSingleItemTable(TFBlocks.QUEST_RAM_TROPHY.get().asItem()));

		add(TFBlocks.ZOMBIE_SKULL_CANDLE, dropWithoutSilk(Blocks.ZOMBIE_HEAD));
		add(TFBlocks.ZOMBIE_WALL_SKULL_CANDLE, dropWithoutSilk(Blocks.ZOMBIE_HEAD));
		add(TFBlocks.SKELETON_SKULL_CANDLE, dropWithoutSilk(Blocks.SKELETON_SKULL));
		add(TFBlocks.SKELETON_WALL_SKULL_CANDLE, dropWithoutSilk(Blocks.SKELETON_SKULL));
		add(TFBlocks.WITHER_SKELE_SKULL_CANDLE, dropWithoutSilk(Blocks.WITHER_SKELETON_SKULL));
		add(TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE, dropWithoutSilk(Blocks.WITHER_SKELETON_SKULL));
		add(TFBlocks.CREEPER_SKULL_CANDLE, dropWithoutSilk(Blocks.CREEPER_HEAD));
		add(TFBlocks.CREEPER_WALL_SKULL_CANDLE, dropWithoutSilk(Blocks.CREEPER_HEAD));
		add(TFBlocks.PLAYER_SKULL_CANDLE, dropWithoutSilk(Blocks.PLAYER_HEAD).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("SkullOwner", "SkullOwner")));
		add(TFBlocks.PLAYER_WALL_SKULL_CANDLE, dropWithoutSilk(Blocks.PLAYER_HEAD).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("SkullOwner", "SkullOwner")));

		dropSelf(TFBlocks.IRON_LADDER.get());
		dropSelf(TFBlocks.TWISTED_STONE.get());
		dropSelf(TFBlocks.TWISTED_STONE_PILLAR.get());
		dropSelf(TFBlocks.BOLD_STONE_PILLAR.get());
		registerEmpty(TFBlocks.DEATH_TOME_SPAWNER.get());
		dropWhenSilkTouch(TFBlocks.EMPTY_CANOPY_BOOKSHELF.get());
		//registerDropSelfLootTable(TFBlocks.LAPIS_BLOCK.get());
		add(TFBlocks.KEEPSAKE_CASKET.get(), casketInfo(TFBlocks.KEEPSAKE_CASKET.get()));
		dropSelf(TFBlocks.CANDELABRA.get());
		dropPottedContents(TFBlocks.POTTED_TWILIGHT_OAK_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_CANOPY_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_MANGROVE_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_DARKWOOD_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_HOLLOW_OAK_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_RAINBOW_OAK_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_TIME_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_TRANSFORMATION_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_MINING_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_SORTING_SAPLING.get());
		dropPottedContents(TFBlocks.POTTED_MAYAPPLE.get());
		dropPottedContents(TFBlocks.POTTED_FIDDLEHEAD.get());
		dropPottedContents(TFBlocks.POTTED_MUSHGLOOM.get());
		add(TFBlocks.POTTED_THORN.get(), createSingleItemTable(Items.FLOWER_POT));
		add(TFBlocks.POTTED_GREEN_THORN.get(), createSingleItemTable(Items.FLOWER_POT));
		add(TFBlocks.POTTED_DEAD_THORN.get(), createSingleItemTable(Items.FLOWER_POT));

		dropSelf(TFBlocks.OAK_BANISTER);
		dropSelf(TFBlocks.SPRUCE_BANISTER);
		dropSelf(TFBlocks.BIRCH_BANISTER);
		dropSelf(TFBlocks.JUNGLE_BANISTER);
		dropSelf(TFBlocks.ACACIA_BANISTER);
		dropSelf(TFBlocks.DARK_OAK_BANISTER);
		dropSelf(TFBlocks.CRIMSON_BANISTER);
		dropSelf(TFBlocks.WARPED_BANISTER);

		add(TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL));
		add(TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL));
		add(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL));
		add(TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL, hollowLog(TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL));

		add(TFBlocks.HOLLOW_OAK_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_OAK_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL));
		add(TFBlocks.HOLLOW_WARPED_STEM_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_WARPED_STEM_VERTICAL));
		add(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_DARK_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_DARK_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_TIME_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_TIME_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_MINING_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_MINING_LOG_VERTICAL));
		add(TFBlocks.HOLLOW_SORTING_LOG_VERTICAL, verticalHollowLog(TFBlocks.HOLLOW_SORTING_LOG_VERTICAL));

		add(TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE));
		add(TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE));
		add(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE));
		add(TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE, hollowLog(TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE));


		dropSelf(TFBlocks.TWILIGHT_OAK_LOG);
		dropSelf(TFBlocks.STRIPPED_TWILIGHT_OAK_LOG);
		dropSelf(TFBlocks.TWILIGHT_OAK_WOOD);
		dropSelf(TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD);
		dropSelf(TFBlocks.TWILIGHT_OAK_SAPLING);
		add(TFBlocks.TWILIGHT_OAK_LEAVES, createLeavesDrops(TFBlocks.TWILIGHT_OAK_LEAVES, TFBlocks.TWILIGHT_OAK_SAPLING, DEFAULT_SAPLING_DROP_RATES));
		dropSelf(TFBlocks.RAINBOW_OAK_SAPLING);
		add(TFBlocks.RAINBOW_OAK_LEAVES, createLeavesDrops(TFBlocks.RAINBOW_OAK_LEAVES, TFBlocks.RAINBOW_OAK_SAPLING, RARE_SAPLING_DROP_RATES));
		dropSelf(TFBlocks.HOLLOW_OAK_SAPLING);
		dropSelf(TFBlocks.TWILIGHT_OAK_PLANKS);
		dropSelf(TFBlocks.TWILIGHT_OAK_STAIRS);
		add(TFBlocks.TWILIGHT_OAK_SLAB, createSlabItemTable(TFBlocks.TWILIGHT_OAK_SLAB));
		dropSelf(TFBlocks.TWILIGHT_OAK_BUTTON);
		dropSelf(TFBlocks.TWILIGHT_OAK_FENCE);
		dropSelf(TFBlocks.TWILIGHT_OAK_GATE);
		dropSelf(TFBlocks.TWILIGHT_OAK_PLATE);
		add(TFBlocks.TWILIGHT_OAK_DOOR, createSinglePropConditionTable(TFBlocks.TWILIGHT_OAK_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.TWILIGHT_OAK_TRAPDOOR);
		add(TFBlocks.TWILIGHT_OAK_SIGN, createSingleItemTable(TFBlocks.TWILIGHT_OAK_SIGN.asItem()));
		add(TFBlocks.TWILIGHT_WALL_SIGN, createSingleItemTable(TFBlocks.TWILIGHT_OAK_SIGN.asItem()));
		dropSelf(TFBlocks.TWILIGHT_OAK_BANISTER);
		dropSelf(TFBlocks.TWILIGHT_OAK_CHEST.get());

		dropSelf(TFBlocks.CANOPY_LOG);
		dropSelf(TFBlocks.STRIPPED_CANOPY_LOG);
		dropSelf(TFBlocks.CANOPY_WOOD);
		dropSelf(TFBlocks.STRIPPED_CANOPY_WOOD);
		dropSelf(TFBlocks.CANOPY_SAPLING);
		add(TFBlocks.CANOPY_LEAVES, createLeavesDrops(TFBlocks.CANOPY_LEAVES, TFBlocks.CANOPY_SAPLING, DEFAULT_SAPLING_DROP_RATES));
		dropSelf(TFBlocks.CANOPY_PLANKS);
		dropSelf(TFBlocks.CANOPY_STAIRS);
		add(TFBlocks.CANOPY_SLAB, createSlabItemTable(TFBlocks.CANOPY_SLAB));
		dropSelf(TFBlocks.CANOPY_BUTTON);
		dropSelf(TFBlocks.CANOPY_FENCE);
		dropSelf(TFBlocks.CANOPY_GATE);
		dropSelf(TFBlocks.CANOPY_PLATE);
		add(TFBlocks.CANOPY_DOOR, createSinglePropConditionTable(TFBlocks.CANOPY_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.CANOPY_TRAPDOOR);
		add(TFBlocks.CANOPY_SIGN, createSingleItemTable(TFBlocks.CANOPY_SIGN.asItem()));
		add(TFBlocks.CANOPY_WALL_SIGN, createSingleItemTable(TFBlocks.CANOPY_SIGN.asItem()));
		add(TFBlocks.CANOPY_BOOKSHELF, createSingleItemTableWithSilkTouch(TFBlocks.CANOPY_BOOKSHELF, Items.BOOK, ConstantValue.exactly(3.0F)));
		dropSelf(TFBlocks.CANOPY_BANISTER);
		dropSelf(TFBlocks.CANOPY_CHEST.get());

		dropSelf(TFBlocks.MANGROVE_LOG);
		dropSelf(TFBlocks.STRIPPED_MANGROVE_LOG);
		dropSelf(TFBlocks.MANGROVE_WOOD);
		dropSelf(TFBlocks.STRIPPED_MANGROVE_WOOD);
		dropSelf(TFBlocks.MANGROVE_SAPLING);
		add(TFBlocks.MANGROVE_LEAVES, createLeavesDrops(TFBlocks.MANGROVE_LEAVES, TFBlocks.MANGROVE_SAPLING, DEFAULT_SAPLING_DROP_RATES));
		dropSelf(TFBlocks.MANGROVE_PLANKS);
		dropSelf(TFBlocks.MANGROVE_STAIRS);
		add(TFBlocks.MANGROVE_SLAB, createSlabItemTable(TFBlocks.MANGROVE_SLAB));
		dropSelf(TFBlocks.MANGROVE_BUTTON);
		dropSelf(TFBlocks.MANGROVE_FENCE);
		dropSelf(TFBlocks.MANGROVE_GATE);
		dropSelf(TFBlocks.MANGROVE_PLATE);
		add(TFBlocks.MANGROVE_DOOR, createSinglePropConditionTable(TFBlocks.MANGROVE_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.MANGROVE_TRAPDOOR);
		add(TFBlocks.MANGROVE_SIGN, createSingleItemTable(TFBlocks.MANGROVE_SIGN.asItem()));
		add(TFBlocks.MANGROVE_WALL_SIGN, createSingleItemTable(TFBlocks.MANGROVE_SIGN.asItem()));
		dropSelf(TFBlocks.MANGROVE_BANISTER);
		dropSelf(TFBlocks.MANGROVE_CHEST.get());

		dropSelf(TFBlocks.DARK_LOG);
		dropSelf(TFBlocks.STRIPPED_DARK_LOG);
		dropSelf(TFBlocks.DARK_WOOD);
		dropSelf(TFBlocks.STRIPPED_DARK_WOOD);
		dropSelf(TFBlocks.DARKWOOD_SAPLING);
		add(TFBlocks.DARK_LEAVES, createLeavesDrops(TFBlocks.DARK_LEAVES, TFBlocks.DARKWOOD_SAPLING, RARE_SAPLING_DROP_RATES));
		add(TFBlocks.HARDENED_DARK_LEAVES, createLeavesDrops(TFBlocks.DARK_LEAVES, TFBlocks.DARKWOOD_SAPLING, RARE_SAPLING_DROP_RATES));
		dropSelf(TFBlocks.DARK_PLANKS);
		dropSelf(TFBlocks.DARK_STAIRS);
		add(TFBlocks.DARK_SLAB, createSlabItemTable(TFBlocks.DARK_SLAB));
		dropSelf(TFBlocks.DARK_BUTTON);
		dropSelf(TFBlocks.DARK_FENCE);
		dropSelf(TFBlocks.DARK_GATE);
		dropSelf(TFBlocks.DARK_PLATE);
		add(TFBlocks.DARK_DOOR, createSinglePropConditionTable(TFBlocks.DARK_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.DARK_TRAPDOOR);
		add(TFBlocks.DARKWOOD_SIGN, createSingleItemTable(TFBlocks.DARKWOOD_SIGN.asItem()));
		add(TFBlocks.DARKWOOD_WALL_SIGN, createSingleItemTable(TFBlocks.DARKWOOD_SIGN.asItem()));
		dropSelf(TFBlocks.DARKWOOD_BANISTER);
		dropSelf(TFBlocks.DARKWOOD_CHEST.get());

		dropSelf(TFBlocks.TIME_LOG);
		dropSelf(TFBlocks.STRIPPED_TIME_LOG);
		dropSelf(TFBlocks.TIME_WOOD);
		dropSelf(TFBlocks.STRIPPED_TIME_WOOD);
		dropOther(TFBlocks.TIME_LOG_CORE, TFBlocks.TIME_LOG);
		dropSelf(TFBlocks.TIME_SAPLING);
		registerLeavesNoSapling(TFBlocks.TIME_LEAVES);
		dropSelf(TFBlocks.TIME_PLANKS);
		dropSelf(TFBlocks.TIME_STAIRS);
		add(TFBlocks.TIME_SLAB, createSlabItemTable(TFBlocks.TIME_SLAB));
		dropSelf(TFBlocks.TIME_BUTTON);
		dropSelf(TFBlocks.TIME_FENCE);
		dropSelf(TFBlocks.TIME_GATE);
		dropSelf(TFBlocks.TIME_PLATE);
		add(TFBlocks.TIME_DOOR, createSinglePropConditionTable(TFBlocks.TIME_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.TIME_TRAPDOOR);
		add(TFBlocks.TIME_SIGN, createSingleItemTable(TFBlocks.TIME_SIGN.asItem()));
		add(TFBlocks.TIME_WALL_SIGN, createSingleItemTable(TFBlocks.TIME_SIGN.asItem()));
		dropSelf(TFBlocks.TIME_BANISTER);
		dropSelf(TFBlocks.TIME_CHEST.get());

		dropSelf(TFBlocks.TRANSFORMATION_LOG);
		dropSelf(TFBlocks.STRIPPED_TRANSFORMATION_LOG);
		dropSelf(TFBlocks.TRANSFORMATION_WOOD);
		dropSelf(TFBlocks.STRIPPED_TRANSFORMATION_WOOD);
		dropOther(TFBlocks.TRANSFORMATION_LOG_CORE, TFBlocks.TRANSFORMATION_LOG);
		dropSelf(TFBlocks.TRANSFORMATION_SAPLING);
		registerLeavesNoSapling(TFBlocks.TRANSFORMATION_LEAVES);
		dropSelf(TFBlocks.TRANSFORMATION_PLANKS);
		dropSelf(TFBlocks.TRANSFORMATION_STAIRS);
		add(TFBlocks.TRANSFORMATION_SLAB, createSlabItemTable(TFBlocks.TRANSFORMATION_SLAB));
		dropSelf(TFBlocks.TRANSFORMATION_BUTTON);
		dropSelf(TFBlocks.TRANSFORMATION_FENCE);
		dropSelf(TFBlocks.TRANSFORMATION_GATE);
		dropSelf(TFBlocks.TRANSFORMATION_PLATE);
		add(TFBlocks.TRANSFORMATION_DOOR, createSinglePropConditionTable(TFBlocks.TRANSFORMATION_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.TRANSFORMATION_TRAPDOOR);
		add(TFBlocks.TRANSFORMATION_SIGN, createSingleItemTable(TFBlocks.TRANSFORMATION_SIGN.asItem()));
		add(TFBlocks.TRANSFORMATION_WALL_SIGN, createSingleItemTable(TFBlocks.TRANSFORMATION_SIGN.asItem()));
		dropSelf(TFBlocks.TRANSFORMATION_BANISTER);
		dropSelf(TFBlocks.TRANSFORMATION_CHEST.get());

		dropSelf(TFBlocks.MINING_LOG);
		dropSelf(TFBlocks.STRIPPED_MINING_LOG);
		dropSelf(TFBlocks.MINING_WOOD);
		dropSelf(TFBlocks.STRIPPED_MINING_WOOD);
		dropOther(TFBlocks.MINING_LOG_CORE, TFBlocks.MINING_LOG);
		dropSelf(TFBlocks.MINING_SAPLING);
		registerLeavesNoSapling(TFBlocks.MINING_LEAVES);
		dropSelf(TFBlocks.MINING_PLANKS);
		dropSelf(TFBlocks.MINING_STAIRS);
		add(TFBlocks.MINING_SLAB, createSlabItemTable(TFBlocks.MINING_SLAB));
		dropSelf(TFBlocks.MINING_BUTTON);
		dropSelf(TFBlocks.MINING_FENCE);
		dropSelf(TFBlocks.MINING_GATE);
		dropSelf(TFBlocks.MINING_PLATE);
		add(TFBlocks.MINING_DOOR, createSinglePropConditionTable(TFBlocks.MINING_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.MINING_TRAPDOOR);
		add(TFBlocks.MINING_SIGN, createSingleItemTable(TFBlocks.MINING_SIGN.asItem()));
		add(TFBlocks.MINING_WALL_SIGN, createSingleItemTable(TFBlocks.MINING_SIGN.asItem()));
		dropSelf(TFBlocks.MINING_BANISTER);
		dropSelf(TFBlocks.MINING_CHEST.get());

		dropSelf(TFBlocks.SORTING_LOG);
		dropSelf(TFBlocks.STRIPPED_SORTING_LOG);
		dropSelf(TFBlocks.SORTING_WOOD);
		dropSelf(TFBlocks.STRIPPED_SORTING_WOOD);
		dropOther(TFBlocks.SORTING_LOG_CORE, TFBlocks.SORTING_LOG);
		dropSelf(TFBlocks.SORTING_SAPLING);
		registerLeavesNoSapling(TFBlocks.SORTING_LEAVES);
		dropSelf(TFBlocks.SORTING_PLANKS);
		dropSelf(TFBlocks.SORTING_STAIRS);
		add(TFBlocks.SORTING_SLAB, createSlabItemTable(TFBlocks.SORTING_SLAB));
		dropSelf(TFBlocks.SORTING_BUTTON);
		dropSelf(TFBlocks.SORTING_FENCE);
		dropSelf(TFBlocks.SORTING_GATE);
		dropSelf(TFBlocks.SORTING_PLATE);
		add(TFBlocks.SORTING_DOOR, createSinglePropConditionTable(TFBlocks.SORTING_DOOR, DoorBlock.HALF, DoubleBlockHalf.LOWER));
		dropSelf(TFBlocks.SORTING_TRAPDOOR);
		add(TFBlocks.SORTING_SIGN, createSingleItemTable(TFBlocks.SORTING_SIGN.asItem()));
		add(TFBlocks.SORTING_WALL_SIGN, createSingleItemTable(TFBlocks.SORTING_SIGN.asItem()));
		dropSelf(TFBlocks.SORTING_BANISTER);
		dropSelf(TFBlocks.SORTING_CHEST.get());

	}

	private void registerLeavesNoSapling(Block leaves) {
		LootPoolEntryContainer.Builder<?> sticks = applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK)
				.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
				.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)));
		add(leaves, createSilkTouchOrShearsDispatchTable(leaves, sticks));
	}

	private LootTable.Builder hollowLog(Block log) {
		LootItemCondition.Builder HAS_SILK_TOUCH = BlockLoot.HAS_SILK_TOUCH;
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(log.asItem()).when(HAS_SILK_TOUCH).otherwise(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Blocks.GRASS).when(HAS_SILK_TOUCH).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogHorizontal.VARIANT, HollowLogVariants.Horizontal.MOSS_AND_GRASS)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(TFBlocks.MOSS_PATCH).when(HAS_SILK_TOUCH).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogHorizontal.VARIANT, HollowLogVariants.Horizontal.MOSS_AND_GRASS)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(TFBlocks.MOSS_PATCH).when(HAS_SILK_TOUCH).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogHorizontal.VARIANT, HollowLogVariants.Horizontal.MOSS)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Items.SNOWBALL).when(HAS_SILK_TOUCH).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogHorizontal.VARIANT, HollowLogVariants.Horizontal.SNOW)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Blocks.VINE).when(HAS_SILK_TOUCH).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogClimbable.VARIANT, HollowLogVariants.Climbable.VINE)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Blocks.LADDER).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogClimbable.VARIANT, HollowLogVariants.Climbable.LADDER)))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(Blocks.LADDER).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(log).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HollowLogClimbable.VARIANT, HollowLogVariants.Climbable.LADDER_WATERLOGGED)))));
	}

	private LootTable.Builder verticalHollowLog(Block log) {
		LootItemCondition.Builder HAS_SILK_TOUCH = BlockLoot.HAS_SILK_TOUCH;
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(log.asItem()).when(HAS_SILK_TOUCH).otherwise(LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
	}

	// [VanillaCopy] super.droppingWithChancesAndSticks, but non-silk touch parameter can be an item instead of a block
	private static LootTable.Builder silkAndStick(Block block, ItemLike nonSilk, float... nonSilkFortune) {
		LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = BlockLoot.HAS_NO_SHEARS_OR_SILK_TOUCH;
		return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(nonSilk.asItem())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, nonSilkFortune))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}

	private static LootTable.Builder casketInfo(Block block) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).apply(CopyBlockState.copyState(block).copy(KeepsakeCasketBlock.BREAKAGE)));
	}

	private static LootTable.Builder particleSpawner() {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(TFBlocks.FIREFLY_SPAWNER, LootItem.lootTableItem(TFBlocks.FIREFLY_SPAWNER))))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(TFBlocks.FIREFLY)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 2))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 3))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 4))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 5))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(5.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 6))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(6.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 7))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(7.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 8))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(8.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 9))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(9.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(TFBlocks.FIREFLY_SPAWNER).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(AbstractParticleSpawnerBlock.RADIUS, 10))))));
	}

	protected static LootTable.Builder torchberryPlant(Block pBlock) {
		LootItemCondition.Builder HAS_SHEARS = BlockLoot.HAS_SHEARS;
		return LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(pBlock).when(HAS_SHEARS)))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.add(LootItem.lootTableItem(TFItems.TORCHBERRIES)
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TorchberryPlantBlock.HAS_BERRIES, true)))));
	}

	private static LootTable.Builder dropWithoutSilk(Block block) {
		LootItemCondition.Builder HAS_SILK_TOUCH = BlockLoot.HAS_SILK_TOUCH;
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_SILK_TOUCH.invert()).add(LootItem.lootTableItem(block)));
	}

	private void registerEmpty(Block b) {
		add(b, LootTable.lootTable());
	}

	//@Override
	protected Iterable<Block> getKnownBlocks() {
		// todo 1.15 once all blockitems are ported, change this to all TF blocks, so an error will be thrown if we're missing any tables
		return knownBlocks;
	}
}

package io.github.gabriel.plotwarden.purpur.world

import io.github.gabriel.plotwarden.manager.PlotWorldManager
import io.github.gabriel.plotwarden.manager.decorator.PlotWorldDecorator
import io.github.gabriel.plotwarden.purpur.service.ChunkRelativePlotWorldDecorator
import io.github.gabriel.plotwarden.struct.PlotWorldSpaceType
import io.github.gabriel.plotwarden.struct.Vector2
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*

class PlotChunkGenerator(val worldManager: PlotWorldManager): ChunkGenerator() {
    var decorator: (ChunkData, Vector2) -> PlotWorldDecorator
            = { chunkData, coordinates -> ChunkRelativePlotWorldDecorator(chunkData, coordinates) }
    val defaultHeight = worldManager.world.defaultHeight

    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        for (x in 0..15) {
            for (z in 0..15) {
                for (y in 0..15) {
                    chunkData.setBlock(x, worldInfo.minHeight, z, Material.BEDROCK)
                }
                val (blockX, blockZ) = x + (chunkX * 16) to z + (chunkZ * 16)
                val spaceType = worldManager.getSpaceType(blockX, blockZ)
                val blockVector = Vector2(blockZ, blockZ)

                if (spaceType == PlotWorldSpaceType.Road) {
                    for (y in worldInfo.minHeight + 1..defaultHeight) {
                        chunkData.setBlock(x, y, z, Material.NETHER_BRICKS)
                    }

                    if (worldManager.painter == null)
                        return

                    val decorator = decorator(chunkData, Vector2(x, z))
                    val relativeVector = worldManager.relativeVector(blockVector)
                    if (worldManager.isCrossRoad(blockVector)) {
                        worldManager.painter?.paintCrossRoad(relativeVector.minus(1, 1), decorator)
                    } else {
                        worldManager.painter?.paintNormalRoad(relativeVector.minus(3, 3), decorator)
                    }
                } else if (spaceType == PlotWorldSpaceType.Border) {
                    for (y in worldInfo.minHeight + 1..defaultHeight) {
                        chunkData.setBlock(x, y, z, Material.BEDROCK)
                    }
                    chunkData.setBlock(x, defaultHeight + 1, z, Material.BLACKSTONE_SLAB)
                } else if (spaceType == PlotWorldSpaceType.Plot) {
                    for (y in worldInfo.minHeight + 1 until defaultHeight) {
                        chunkData.setBlock(x, y, z, Material.DIRT)
                    }
                    chunkData.setBlock(x, defaultHeight, z, Material.NETHERRACK)
                }
            }
        }
    }

    override fun getFixedSpawnLocation(world: World, random: Random): Location =
        Location(world, 0.0, defaultHeight.toDouble()+1, 0.0)

    private val biomeProvider = object: BiomeProvider() {
        override fun getBiome(worldInfo: WorldInfo, x: Int, y: Int, z: Int): Biome =
            Biome.PLAINS

        override fun getBiomes(worldInfo: WorldInfo): List<Biome> =
            supportedBiomes
    }

    override fun getDefaultBiomeProvider(worldInfo: WorldInfo): BiomeProvider {
        return biomeProvider
    }

    val supportedBiomes = listOf(
        Biome.OCEAN,
        Biome.PLAINS,
        Biome.DESERT,
        Biome.WINDSWEPT_HILLS,
        Biome.FOREST,
        Biome.TAIGA,
        Biome.SWAMP,
        Biome.MANGROVE_SWAMP,
        Biome.RIVER,
        Biome.NETHER_WASTES,
        Biome.THE_END,
        Biome.FROZEN_OCEAN,
        Biome.FROZEN_RIVER,
        Biome.SNOWY_PLAINS,
        Biome.MUSHROOM_FIELDS,
        Biome.BEACH,
        Biome.JUNGLE,
        Biome.SPARSE_JUNGLE,
        Biome.DEEP_OCEAN,
        Biome.STONY_SHORE,
        Biome.SNOWY_BEACH,
        Biome.BIRCH_FOREST,
        Biome.DARK_FOREST,
        Biome.SNOWY_TAIGA,
        Biome.OLD_GROWTH_PINE_TAIGA,
        Biome.WINDSWEPT_FOREST,
        Biome.SAVANNA,
        Biome.SAVANNA_PLATEAU,
        Biome.BADLANDS,
        Biome.WOODED_BADLANDS,
        Biome.SMALL_END_ISLANDS,
        Biome.END_MIDLANDS,
        Biome.END_HIGHLANDS,
        Biome.END_BARRENS,
        Biome.WARM_OCEAN,
        Biome.LUKEWARM_OCEAN,
        Biome.COLD_OCEAN,
        Biome.DEEP_LUKEWARM_OCEAN,
        Biome.DEEP_COLD_OCEAN,
        Biome.DEEP_FROZEN_OCEAN,
        Biome.THE_VOID,
        Biome.SUNFLOWER_PLAINS,
        Biome.WINDSWEPT_GRAVELLY_HILLS,
        Biome.FLOWER_FOREST,
        Biome.ICE_SPIKES,
        Biome.OLD_GROWTH_BIRCH_FOREST,
        Biome.OLD_GROWTH_SPRUCE_TAIGA,
        Biome.WINDSWEPT_SAVANNA,
        Biome.ERODED_BADLANDS,
        Biome.BAMBOO_JUNGLE,
        Biome.SOUL_SAND_VALLEY,
        Biome.CRIMSON_FOREST,
        Biome.WARPED_FOREST,
        Biome.BASALT_DELTAS,
        Biome.DRIPSTONE_CAVES,
        Biome.LUSH_CAVES,
        Biome.DEEP_DARK,
        Biome.MEADOW,
        Biome.GROVE,
        Biome.SNOWY_SLOPES,
        Biome.FROZEN_PEAKS,
        Biome.JAGGED_PEAKS,
        Biome.STONY_PEAKS
    )
}
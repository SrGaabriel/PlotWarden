package io.github.gabriel.plotwarden.purpur.util

import org.bukkit.Material
import org.bukkit.block.Biome

val Biome.symbolicBlock get() = when (this) {
    Biome.PLAINS -> Material.GRASS_BLOCK
    Biome.DESERT -> Material.SAND
    Biome.FOREST -> Material.BIRCH_SAPLING
    Biome.TAIGA -> Material.ACACIA_WOOD
    Biome.SWAMP -> Material.CAULDRON
    Biome.MANGROVE_SWAMP -> Material.VINE
    Biome.SNOWY_PLAINS -> Material.SNOWBALL
    Biome.MUSHROOM_FIELDS -> Material.BROWN_MUSHROOM
    else -> null
}
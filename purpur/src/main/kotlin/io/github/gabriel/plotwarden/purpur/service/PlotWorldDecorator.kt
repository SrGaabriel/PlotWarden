package io.github.gabriel.plotwarden.purpur.service

import io.github.gabriel.plotwarden.manager.decorator.PlotWorldDecorator
import io.github.gabriel.plotwarden.struct.Vector2
import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator.ChunkData

class ChunkRelativePlotWorldDecorator(
    val chunkData: ChunkData,
    val chunkRelativeCoordinates: Vector2
): PlotWorldDecorator() {
    override fun draw(y: Int, material: String) {
        chunkData.setBlock(
            chunkRelativeCoordinates.x,
            y,
            chunkRelativeCoordinates.z,
            Material.getMaterial(material) ?: error("Unknown material '$material'"))
    }
}
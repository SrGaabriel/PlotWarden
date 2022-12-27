package io.github.gabriel.plotwarden.database

import io.github.gabriel.plotwarden.manager.PlotWorld
import io.github.gabriel.plotwarden.struct.Vector2
import io.github.gabriel.plotwarden.util.plusOrMinusInverted
import org.jetbrains.exposed.sql.Table
import java.util.UUID

data class Plot(
    val world: PlotWorld,
    val origin: Vector2,
    val owner: UUID,
    val biome: String? = null
)  {
    val end get() = Vector2(origin.x.plusOrMinusInverted(world.plotTotalLength-1), origin.z.plusOrMinusInverted(world.plotTotalWidth-1))
}

object PlotsTable: Table("plots") {
    val owner = uuid("owner")
    val biome = varchar("biome", 24).nullable()

    val originX = integer("originX")
    val originZ = integer("originZ")
}
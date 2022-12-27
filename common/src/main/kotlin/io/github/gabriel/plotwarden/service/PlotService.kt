package io.github.gabriel.plotwarden.service

import io.github.gabriel.plotwarden.database.Plot
import io.github.gabriel.plotwarden.database.PlotsTable
import io.github.gabriel.plotwarden.manager.PlotWorld
import io.github.gabriel.plotwarden.struct.Vector2
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PlotService(private val world: PlotWorld) {
    fun registerPlot(plot: Plot) = transaction {
        PlotsTable.insert {
            it[originX] = plot.origin.x
            it[originZ] = plot.origin.z
            it[owner] = plot.owner
            it[biome] = plot.biome
        }
    }

    fun getPlot(origin: Vector2): Plot? = transaction {
        val resultRow = PlotsTable.select {
            (PlotsTable.originX eq origin.x) and (PlotsTable.originZ eq origin.z)
        }.singleOrNull() ?: return@transaction null

        Plot(
            world = world,
            origin = origin,
            owner = resultRow[PlotsTable.owner],
            biome = resultRow[PlotsTable.biome]
        )
    }

    fun getUserPlots(user: UUID): List<Plot> = transaction {
        val plotsQuery = PlotsTable.select {
            PlotsTable.owner eq user
        }
        plotsQuery.map { resultRow ->
            val origin = Vector2(resultRow[PlotsTable.originX], resultRow[PlotsTable.originZ])
            Plot(
                world = world,
                origin = origin,
                owner = resultRow[PlotsTable.owner],
                biome = resultRow[PlotsTable.biome]
            )
        }
    }

    fun disownPlot(origin: Vector2) = transaction {
        PlotsTable.deleteWhere {
            (originX eq origin.x) and (originZ eq origin.z)
        }
    }
}
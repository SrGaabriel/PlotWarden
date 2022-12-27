package io.github.gabriel.plotwarden.purpur

import io.github.fantasy.KotlinPlugin
import io.github.fantasy.feature.command.Commands
import io.github.gabriel.plotwarden.manager.PlotWorld
import io.github.gabriel.plotwarden.manager.PlotWorldManager
import io.github.gabriel.plotwarden.purpur.command.plot
import io.github.gabriel.plotwarden.purpur.listener.PlotWorldListener
import io.github.gabriel.plotwarden.purpur.service.DatabaseService
import io.github.gabriel.plotwarden.purpur.world.PlotChunkGenerator
import io.github.gabriel.plotwarden.service.PlotService
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator

class PlotWarden: KotlinPlugin() {
    val defaultPlotWorld = PlotWorld("plotworld", 24, 24, 5, 0)
    val plotService: PlotService = PlotService(defaultPlotWorld)
    val plotWorldManager = PlotWorldManager(defaultPlotWorld)

    override fun onStart() {
        val databaseService = DatabaseService(this)
        databaseService.connect()
        databaseService.createTables()

        install(Commands) {
            register(plot)
        }
        Bukkit.getPluginManager().registerEvents(PlotWorldListener(this), this)
    }

    fun isPlotWorld(world: World) = world.name == defaultPlotWorld.name

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator =
        PlotChunkGenerator(plotWorldManager)
}
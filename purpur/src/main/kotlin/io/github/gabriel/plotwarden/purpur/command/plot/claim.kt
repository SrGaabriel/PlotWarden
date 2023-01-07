package io.github.gabriel.plotwarden.purpur.command.plot

import io.github.fantasy.feature.command.struct.Command
import io.github.gabriel.plotwarden.database.Plot
import io.github.gabriel.plotwarden.purpur.PlotWarden
import io.github.gabriel.plotwarden.struct.PlotWorldSpaceType
import io.github.gabriel.plotwarden.struct.Vector2
import org.bukkit.Sound
import org.bukkit.entity.Player

internal fun Command.claim(plugin: PlotWarden) = literal("claim") {
    executor {
        val source = this.source as Player
        val userPlots = plugin.plotService.getUserPlots(source.uniqueId)

        if (userPlots.isNotEmpty()) {
            source.sendMessage("§c§lERROR §fYou have reached your limit of §c1 §fplot per user.")
            return@executor
        }

        val location = source.location
        val spaceType = plugin.plotWorldManager.getSpaceType(location.blockX, location.blockZ)

        if (spaceType != PlotWorldSpaceType.Plot) {
            source.sendMessage("§c§lERROR §fYou must be standing on a plot to claim it.")
            return@executor
        }

        val origin = plugin.plotWorldManager.getPlotOrigin(Vector2(location.blockX, location.blockZ))
        val existingPlot = plugin.plotService.getPlot(origin)

        if (existingPlot != null) {
            source.sendMessage("§c§lERROR §fThis plot is already owned by another player.")
            return@executor
        }

        val plot = Plot(
            world = plugin.defaultPlotWorld,
            origin = origin,
            owner = source.uniqueId,
            biome = null
        )
        plugin.plotService.registerPlot(plot)

        source.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 1f, 1f)
        source.sendMessage("§a§lPLOT §fYou have successfully claimed this plot!")
    }
}

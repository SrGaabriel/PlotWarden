package io.github.gabriel.plotwarden.purpur.command.plot

import io.github.fantasy.feature.command.struct.Command
import io.github.gabriel.plotwarden.purpur.PlotWarden
import org.bukkit.entity.Player

internal fun Command.disband(plugin: PlotWarden) = literal("disband") {
    executor {
        val source = this.source as Player

    }
}
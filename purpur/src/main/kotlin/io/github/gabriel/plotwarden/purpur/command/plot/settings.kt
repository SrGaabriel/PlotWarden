package io.github.gabriel.plotwarden.purpur.command.plot

import io.github.fantasy.feature.command.struct.Command
import io.github.gabriel.plotwarden.purpur.PlotWarden

internal fun Command.settings(plugin: PlotWarden) = literal("settings") {
    executor {

    }
}
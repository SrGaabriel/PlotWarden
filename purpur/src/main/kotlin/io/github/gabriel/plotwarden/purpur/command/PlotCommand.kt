package io.github.gabriel.plotwarden.purpur.command

import io.github.fantasy.feature.command.struct.Command
import io.github.fantasy.feature.command.util.command
import io.github.gabriel.plotwarden.purpur.PlotWarden
import io.github.gabriel.plotwarden.purpur.command.plot.claim

val PlotWarden.plot: Command get() {
    val plugin = this
    return command("plot") {
        claim(plugin)
    }
}
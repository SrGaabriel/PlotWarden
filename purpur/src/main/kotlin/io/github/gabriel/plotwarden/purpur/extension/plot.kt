package io.github.gabriel.plotwarden.purpur.extension

import io.github.gabriel.plotwarden.manager.PlotWorldManager
import org.bukkit.Location

fun PlotWorldManager.getSpaceType(location: Location) = getSpaceType(location.blockX, location.blockZ)
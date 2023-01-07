package io.github.gabriel.plotwarden.purpur.listener

import io.github.gabriel.plotwarden.purpur.PlotWarden
import io.github.gabriel.plotwarden.struct.PlotWorldSpaceType
import io.github.gabriel.plotwarden.struct.Vector2
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.CreatureSpawnEvent

class PlotWorldListener(val plugin: PlotWarden): Listener {
    @EventHandler
    fun onCreatureSpawn(event: CreatureSpawnEvent) {
        if (event.spawnReason != CreatureSpawnEvent.SpawnReason.NATURAL)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        event.isCancelled = event.isCancelled || checkPlotPermissions(event, event.player)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = event.isCancelled || checkPlotPermissions(event, event.player)
    }

    private fun checkPlotPermissions(event: BlockEvent, player: Player): Boolean {
        if (!plugin.isPlotWorld(event.block.world))
            return false
        val spaceType = plugin.plotWorldManager.getSpaceType(event.block.x, event.block.z)

        if (spaceType != PlotWorldSpaceType.Plot) {
            player.sendMessage("§c§lERROR §fYou don't have enough permissions to build in this area.")
            return true
        }

        val vector = Vector2(event.block.x, event.block.z)
        val origin = plugin.plotWorldManager.getPlotOrigin(vector)
        val plot = plugin.plotService.getPlot(origin)
        if (plot == null) {
            player.sendMessage("§c§lERROR §fYou don't have enough permissions to build in this area.")
            return true
        }
        val result = plot.owner != player.uniqueId
        if (result) {
            player.sendMessage("§c§lERROR §fYou must ask for permissions from this plots' owner to build here.")
        }
        return result
    }
}
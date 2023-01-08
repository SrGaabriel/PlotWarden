package io.github.gabriel.plotwarden.purpur.menu

import io.github.fantasy.menu.menu
import io.github.fantasy.util.itemStack
import io.github.fantasy.util.name
import io.github.gabriel.plotwarden.database.Plot
import io.github.gabriel.plotwarden.purpur.PlotWarden
import io.github.gabriel.plotwarden.purpur.util.symbolicBlock
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

val PlotWarden.settings get() = menu(size = 36) {
    onRender {
        val plot = data["player.plot"] as Plot
        title = Component.text("${player.name}'s Plot Settings")

        item(11, itemStack(Material.DEAD_BUSH).name("§aBiome")) {
            onClick {
                close(InventoryCloseEvent.Reason.OPEN_NEW)
                open()
            }
        }
    }
}

private val PlotWarden.biomeSettings get() = menu(54) {
    onRender {
        val plot = data["player.plot"] as Plot
        title = Component.text("${player.name}'s Plot Biome Settings")
    }
    pagination {
        fixed(Biome.values().asList(), 45) { page, biome ->
            item(0, itemStack(biome.symbolicBlock).name())
        }
    }
}
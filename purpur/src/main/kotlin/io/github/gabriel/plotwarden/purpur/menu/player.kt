package io.github.gabriel.plotwarden.purpur.menu

import io.github.fantasy.menu.FantasyMenu
import io.github.fantasy.menu.menu
import io.github.fantasy.util.head
import io.github.fantasy.util.itemStack
import io.github.fantasy.util.name
import io.github.gabriel.plotwarden.purpur.PlotWarden
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryCloseEvent

val PlotWarden.playerSearch get() = menu(54) {
    pagination {
        fixedQuery(45, query = { Bukkit.getOnlinePlayers() }) { index, player ->
            item(index, itemStack(Material.PLAYER_HEAD, 3).name(player.name).head(player)) {
                onClick {
                    if (!player.isOnline) {
                        player.sendMessage("Not online :(")
                        return@onClick
                    }
                    data["Player-Search"] = player
                    close(InventoryCloseEvent.Reason.OPEN_NEW)
                    (data["Original-Menu"] as FantasyMenu).open(player, data)
                }
            }
        }
    }
}
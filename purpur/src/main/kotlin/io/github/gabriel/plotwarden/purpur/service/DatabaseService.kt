package io.github.gabriel.plotwarden.purpur.service

import io.github.gabriel.plotwarden.database.PlotsTable
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

class DatabaseService(private val plugin: JavaPlugin) {
    fun connect() {
        val plotsFile = File(plugin.dataFolder, "plots.db")
        if (!plotsFile.exists()) {
            plugin.dataFolder.mkdir()
            plotsFile.createNewFile()
        }
        Database.connect("jdbc:sqlite:$plotsFile", "org.sqlite.JDBC")
    }

    fun createTables() = transaction {
        SchemaUtils.createMissingTablesAndColumns(PlotsTable)
    }
}
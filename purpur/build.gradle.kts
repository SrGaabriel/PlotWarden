@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("jvm")
    alias(libs.plugins.shadow)
    alias(libs.plugins.plugin.yml)
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.purpurmc.org/snapshots") {
        name = "PurpurMC"
    }
}

dependencies {
    api(project(":common"))
    compileOnly(libs.purpur.api)
    compileOnly(libs.brigadier)

    compileOnly(libs.fantasy.core)
    implementation(libs.fantasy.menus)
    implementation(libs.fantasy.commands)
}

bukkit {
    name = "PlotWarden"
    version = "1.0.0"
    main = "io.github.gabriel.plotwarden.purpur.PlotWarden"
    author = "SrGaabriel"
}
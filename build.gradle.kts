@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

subprojects {
    group = "io.github.gabriel.plotwarden"
    version = "1.0-SNAPSHOT"
}

repositories {
    mavenCentral()
}
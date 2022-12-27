plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    api(libs.exposed.core)
    api(libs.exposed.dao)
    api(libs.exposed.jdbc)
}
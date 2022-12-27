rootProject.name = "PlotWarden"

include("common")
include("purpur")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

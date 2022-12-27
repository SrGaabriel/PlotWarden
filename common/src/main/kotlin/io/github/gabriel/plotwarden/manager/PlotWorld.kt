package io.github.gabriel.plotwarden.manager

data class PlotWorld(
    val name: String,
    val plotLength: Int,
    val plotWidth: Int,
    val roadLength: Int,
    val defaultHeight: Int
) {
    val plotTotalLength: Int = plotLength + 2
    val plotTotalWidth: Int = plotLength + 2
}
package io.github.gabriel.plotwarden.manager.decorator

import io.github.gabriel.plotwarden.struct.Vector2

interface PlotWorldPainter {
    fun paintCrossRoad(roadRelativeVector: Vector2, decorator: PlotWorldDecorator)
}
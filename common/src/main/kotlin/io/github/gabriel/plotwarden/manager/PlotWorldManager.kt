package io.github.gabriel.plotwarden.manager

import io.github.gabriel.plotwarden.manager.decorator.PlotWorldPainter
import io.github.gabriel.plotwarden.struct.PlotWorldSpaceType
import io.github.gabriel.plotwarden.struct.Vector2
import io.github.gabriel.plotwarden.util.plusOrMinusInverted
import kotlin.math.abs

class PlotWorldManager(val world: PlotWorld) {
    private val roadLimit = world.roadLength/2

    private val plotTotalLength = world.plotTotalLength
    private val plotTotalWidth = world.plotTotalWidth

    var painter: PlotWorldPainter? = null

    fun isCrossRoad(location: Vector2, assertLocationIsARoad: Boolean = false): Boolean {
        if (assertLocationIsARoad)
            assert(getSpaceType(location.x, location.z) == PlotWorldSpaceType.Road)

        return (abs(location.x) + roadLimit + 1) % (plotTotalLength + world.roadLength) in 1..world.roadLength
                && (abs(location.z) + roadLimit + 1) % (plotTotalWidth + world.roadLength) in 1..world.roadLength
    }

    fun getPlotOrigin(location: Vector2, assertLocationIsAPlot: Boolean = false): Vector2 {
        if (assertLocationIsAPlot)
            assert(getSpaceType(location.x, location.z) == PlotWorldSpaceType.Plot)

        val scaledX = location.x % (world.plotTotalLength + world.roadLength)
        val scaledZ = location.z % (world.plotTotalWidth + world.roadLength)

        val xDifference = abs(scaledX.plusOrMinusInverted(world.roadLength - 1))
        val zDifference = abs(scaledZ.plusOrMinusInverted(world.roadLength - 1))

        val originX = location.x.plusOrMinusInverted(xDifference)
        val originZ = location.z.plusOrMinusInverted(zDifference)

        return Vector2(originX, originZ)
    }

    fun getSpaceType(x: Int, z: Int): PlotWorldSpaceType {
        return when {
            relativeX(x) in 1..world.roadLength
                    || relativeZ(z) in 1..world.roadLength -> PlotWorldSpaceType.Road
            relativeX(x) == world.roadLength + 1
                    || relativeZ(z) == world.roadLength + 1 -> PlotWorldSpaceType.Border
            relativeX(x) == 0
                    || relativeZ(z) == 0 -> PlotWorldSpaceType.Border
            else -> PlotWorldSpaceType.Plot
        }
    }

    fun relativeX(x: Int) =(abs(x) + roadLimit + 1) % (plotTotalLength + world.roadLength)

    fun relativeZ(z: Int) =(abs(z) + roadLimit + 1) % (plotTotalWidth + world.roadLength)

    fun relativeVector(vector: Vector2) = Vector2(
        x = relativeX(vector.x),
        z = relativeZ(vector.z)
    )
}
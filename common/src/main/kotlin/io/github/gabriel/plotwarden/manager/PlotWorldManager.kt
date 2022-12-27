package io.github.gabriel.plotwarden.manager

import io.github.gabriel.plotwarden.struct.PlotWorldSpaceType
import io.github.gabriel.plotwarden.struct.Vector2
import io.github.gabriel.plotwarden.util.plusOrMinusInverted
import kotlin.math.abs

class PlotWorldManager(val world: PlotWorld) {
    private val roadLimit = world.roadLength/2

    private val plotTotalLength = world.plotTotalLength
    private val plotTotalWidth = world.plotTotalWidth

    fun getPlotOrigin(location: Vector2, assertLocationIsAPlot: Boolean = true): Vector2 {
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
            (abs(x) + roadLimit + 1) % (plotTotalLength + world.roadLength) in 1..world.roadLength
                    || (abs(z) + roadLimit + 1) % (plotTotalWidth + world.roadLength) in 1..world.roadLength -> PlotWorldSpaceType.Road
            (abs(x) + roadLimit + 1) % (plotTotalLength + world.roadLength) == world.roadLength + 1
                    || (abs(z) + roadLimit + 1) % (plotTotalWidth + world.roadLength) == world.roadLength + 1 -> PlotWorldSpaceType.Border
            (abs(x) + roadLimit + 1) % (plotTotalLength + world.roadLength) == 0
                    || (abs(z) + roadLimit + 1) % (plotTotalWidth + world.roadLength) == 0 -> PlotWorldSpaceType.Border
            else -> PlotWorldSpaceType.Plot
        }
    }
}
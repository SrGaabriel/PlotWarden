package io.github.gabriel.plotwarden.struct

data class Vector3(val x: Double, val y: Double, val z: Double) {
    val blockX: Int get() = x.toInt()

    val blockY: Int get() = y.toInt()

    val blockZ: Int get() = z.toInt()

    fun flatten(): Vector2 = Vector2(blockX, blockZ)

    companion object {
        @JvmStatic
        fun blockVector(x: Int, y: Int, z: Int) =
            Vector3(x.toDouble(), y.toDouble(), z.toDouble())
    }
}
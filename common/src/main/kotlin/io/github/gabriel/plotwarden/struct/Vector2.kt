package io.github.gabriel.plotwarden.struct

data class Vector2(val x: Int, val z: Int) {
    fun minus(x: Int, z: Int) = Vector2(this.x - x, this.z - z)

    operator fun minus(other: Vector2) = Vector2(this.x - other.x, this.z - other.z)
}
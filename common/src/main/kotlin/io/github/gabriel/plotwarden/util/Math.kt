package io.github.gabriel.plotwarden.util

fun Int.plusOrMinusInverted(other: Int) =
    if (this >= 0) this - other else this + other
package com.mono.rpssim.engine

import androidx.compose.ui.geometry.Offset
import kotlin.math.pow
import kotlin.math.sqrt

object GameMath {

	fun distance(a: Offset, b: Offset): Float {
		return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
	}

	fun reflectVelocity(velocity: Offset, normal: Offset): Offset {
		val n = normal.normalize()
		val dot = velocity.dot(n)
		return velocity - n * (2 * dot)
	}

	private fun Offset.dot(other: Offset): Float = this.x * other.x + this.y * other.y

	fun Offset.normalize(): Offset {
		val mag = sqrt(x * x + y * y)
		return if (mag == 0f) Offset.Zero else Offset(x / mag, y / mag)
	}

	private fun Float.pow(p: Int) = this.toDouble().pow(p).toFloat()

}

package com.mono.rpssim.engine

import androidx.compose.ui.geometry.Offset

enum class ParticleType {
	ROCK, PAPER, SCISSOR;

	fun defeats(other: ParticleType): Boolean = when (this) {
		ROCK -> other == SCISSOR
		PAPER -> other == ROCK
		SCISSOR -> other == PAPER
	}
}

data class Particle(
	val id: Int,
	val type: ParticleType,
	val position: Offset,
	val velocity: Offset,
	val radius: Float = 32f,
)
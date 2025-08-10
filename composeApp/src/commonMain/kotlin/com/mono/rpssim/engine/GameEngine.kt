package com.mono.rpssim.engine

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.mono.rpssim.engine.GameMath.normalize
import kotlin.random.Random

class GameEngine {

	private val _particles = mutableStateListOf<Particle>()
	val particles: List<Particle> get() = _particles

	fun initialize(n: Int, screenSize: Size, speed: Float) {
		_particles.clear()

		repeat(n) {
			val type = ParticleType.entries[it % ParticleType.entries.size]
			val pos = Offset(
				x = Random.nextFloat() * (screenSize.width - 100f),
				y = Random.nextFloat() * (screenSize.height - 100f)
			)
			val angle = Random.nextFloat() * 360f
			val vel = Offset(
				x = speed * kotlin.math.cos(angle),
				y = speed * kotlin.math.sin(angle)
			)

			_particles += Particle(
				id = it,
				type = type,
				position = pos,
				velocity = vel
			)
		}
	}

	fun update(screenSize: Size) {
		val updated = _particles.map { p ->
			val next = p.position + p.velocity

			val bounced = Offset(
				x = if (next.x < 0 || next.x > screenSize.width - p.radius) -p.velocity.x else p.velocity.x,
				y = if (next.y < 0 || next.y > screenSize.height - p.radius) -p.velocity.y else p.velocity.y
			)

			p.copy(position = p.position + bounced, velocity = bounced)
		}

		_particles.clear()
		_particles.addAll(updated)

		handleCollisions()
	}

	private fun handleCollisions() {
		val toUpdate = _particles.toMutableList()

		for (i in toUpdate.indices) {
			for (j in i + 1 until toUpdate.size) {
				val p1 = toUpdate[i]
				val p2 = toUpdate[j]

				if (GameMath.distance(p1.position, p2.position) < p1.radius + p2.radius) {
					val normal = (p2.position - p1.position).normalize()

					val newVel1 = GameMath.reflectVelocity(p1.velocity, normal)
					val newVel2 = GameMath.reflectVelocity(p2.velocity, -normal)

					val winner = resolveWinner(p1, p2)

					val type1 = winner ?: p1.type
					val type2 = winner ?: p2.type

					toUpdate[i] = p1.copy(type = type1, velocity = newVel1)
					toUpdate[j] = p2.copy(type = type2, velocity = newVel2)
				}
			}
		}

		_particles.clear()
		_particles.addAll(toUpdate)
	}

	private fun resolveWinner(p1: Particle, p2: Particle): ParticleType? {
		return when {
			p1.type.defeats(p2.type) -> p1.type
			p2.type.defeats(p1.type) -> p2.type
			else -> null
		}
	}

	fun typeCounts(): Map<ParticleType, Int> =
		_particles.groupingBy { it.type }.eachCount()
}

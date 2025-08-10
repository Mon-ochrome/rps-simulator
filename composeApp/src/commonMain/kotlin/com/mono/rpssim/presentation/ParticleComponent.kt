package com.mono.rpssim.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mono.rpssim.engine.Particle
import com.mono.rpssim.engine.ParticleType
import org.jetbrains.compose.resources.painterResource
import rpssim.composeapp.generated.resources.Res
import rpssim.composeapp.generated.resources.paper
import rpssim.composeapp.generated.resources.rock
import rpssim.composeapp.generated.resources.scissor

@Composable
fun ParticleItem(particle: Particle, modifier: Modifier = Modifier) {
	val imageRes = when (particle.type) {
		ParticleType.ROCK -> Res.drawable.rock
		ParticleType.PAPER -> Res.drawable.paper
		ParticleType.SCISSOR -> Res.drawable.scissor
	}

	Image(
		painter = painterResource(imageRes),
		contentDescription = particle.type.name,
		contentScale = ContentScale.Fit,
		modifier = modifier
			.offset { particle.position.toIntOffset() }
			.then(Modifier)
			.size(particle.radius.dp * 2)
	)
}

fun Offset.toIntOffset() = androidx.compose.ui.unit.IntOffset(x.toInt(), y.toInt())

package com.mono.rpssim.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.mono.rpssim.engine.GameEngine
import com.mono.rpssim.engine.ParticleType
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RpsSimulatorScreen() {
	val gameEngine = remember { GameEngine() }
	var screenSize by remember { mutableStateOf(Size(0f, 0f)) }
	val particles = gameEngine.particles
	val counts = gameEngine.typeCounts()
	var isRunning by remember { mutableStateOf(false) }

	// Game Engine loop
	LaunchedEffect(screenSize, isRunning) {
		if (screenSize.width > 0 && isRunning) {
			gameEngine.initialize(30, screenSize, speed = 5f)
			while (isRunning) {
				gameEngine.update(screenSize)
				delay(16)
			}
		}
	}

	Scaffold(
		containerColor = Color(0xFF0D0D1A),
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "RPS Simulator",
						style = MaterialTheme.typography.headlineSmall,
						color = Color.White
					)
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color(0xFF121222)
				)
			)
		}
	) { innerPadding ->

		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			// Counters
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				RpsCountComponent("Rock", counts[ParticleType.ROCK] ?: 0, Color(0xFF6A5ACD))
				RpsCountComponent("Paper", counts[ParticleType.PAPER] ?: 0, Color(0xFF48C9B0))
				RpsCountComponent("Scissor", counts[ParticleType.SCISSOR] ?: 0, Color(0xFFE74C3C))
			}

			Spacer(modifier = Modifier.height(24.dp))

			// Start/Stop Button
			Button(
				onClick = { isRunning = !isRunning },
				colors = ButtonDefaults.buttonColors(
					containerColor = if (isRunning) Color(0xFFD35400) else Color(0xFF27AE60),
					contentColor = Color.White
				)
			) {
				Text(if (isRunning) "⏹ Stop Simulation" else "▶ Start Simulation")
			}

			Spacer(modifier = Modifier.height(24.dp))

			// Simulation Box
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(Color(0xFF10101A), shape = RoundedCornerShape(16.dp))
					.border(2.dp, Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp))
					.onSizeChanged {
						screenSize = Size(it.width.toFloat() - 64f, it.height.toFloat() - 64f)
					}
			) {
				if (isRunning) {
					particles.forEach {
						ParticleItem(particle = it)
					}
				}
			}
		}
	}
}
package com.mono.rpssim

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.mono.rpssim.presentation.RpsSimulatorScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
	MaterialTheme {
		RpsSimulatorScreen()
	}
}
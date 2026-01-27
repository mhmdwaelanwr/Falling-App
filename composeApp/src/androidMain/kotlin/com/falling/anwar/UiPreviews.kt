package com.falling.anwar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.falling.anwar.ui.screens.AlertScreen
import com.falling.anwar.ui.screens.HomeScreen
import com.falling.anwar.ui.theme.FallingTheme
import kotlinx.datetime.Clock

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FallingTheme {
        HomeScreen(
            lastFallTimestamp = Clock.System.now().toEpochMilliseconds(),
            onOpenDebug = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlertScreenPreview() {
    FallingTheme {
        AlertScreen(
            timestamp = Clock.System.now().toEpochMilliseconds(),
            onAcknowledge = {},
            onCallEmergency = {}
        )
    }
}

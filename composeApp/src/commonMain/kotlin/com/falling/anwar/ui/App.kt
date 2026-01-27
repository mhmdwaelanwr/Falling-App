package com.falling.anwar.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.falling.anwar.domain.*
import com.falling.anwar.ui.screens.AlertScreen
import com.falling.anwar.ui.screens.HomeScreen
import com.falling.anwar.ui.theme.FallingTheme

@Composable
fun App(
    fallStore: FallStore,
    platformActions: PlatformActions,
    mockEventSource: MockFallEventSource
) {
    val uiState by fallStore.uiState.collectAsState()
    val lastFallTimestamp by fallStore.lastFallTimestamp.collectAsState()

    FallingTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(600)) + scaleIn(initialScale = 0.95f, animationSpec = tween(600)))
                        .togetherWith(fadeOut(animationSpec = tween(600)))
                }
            ) { state ->
                when (state) {
                    FallUiState.NORMAL -> HomeScreen(
                        lastFallTimestamp = lastFallTimestamp,
                        onSimulateFall = { fallStore.onEvent(FallEvent.FALL) }
                    )
                    FallUiState.ALERT -> AlertScreen(
                        timestamp = lastFallTimestamp,
                        onAcknowledge = { fallStore.acknowledgeOk() },
                        onCallEmergency = { platformActions.callEmergency() }
                    )
                }
            }
        }
    }
}

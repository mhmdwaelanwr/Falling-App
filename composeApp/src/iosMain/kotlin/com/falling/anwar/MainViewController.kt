package com.falling.anwar

import androidx.compose.ui.window.ComposeUIViewController
import com.falling.anwar.domain.FallStore
import com.falling.anwar.domain.MockFallEventSource
import com.falling.anwar.ui.App
import com.russhwolf.settings.Settings

fun MainViewController() = ComposeUIViewController {
    val alertExecutor = IosPlatformAlertExecutor()
    val platformActions = IosPlatformActions()
    val fallStore = FallStore(Settings(), alertExecutor)
    val mockEventSource = MockFallEventSource()
    
    App(
        fallStore = fallStore,
        platformActions = platformActions,
        mockEventSource = mockEventSource,
        isDebug = true // Usually false in production iOS, but kept true for this dev exercise
    )
}

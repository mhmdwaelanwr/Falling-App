package com.falling.anwar.ui.debug

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.datetime.Clock

@Composable
fun Modifier.hiddenDebugTapTarget(
    tapsRequired: Int = 4,
    windowMs: Long = 1500,
    enabled: Boolean = true,
    onTriggered: () -> Unit
): Modifier {
    if (!enabled) return this

    var tapCount by remember { mutableStateOf(0) }
    var lastTapTime by remember { mutableStateOf(0L) }

    return this.pointerInput(Unit) {
        detectTapGestures {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            if (currentTime - lastTapTime > windowMs) {
                tapCount = 1
            } else {
                tapCount++
            }
            lastTapTime = currentTime

            if (tapCount >= tapsRequired) {
                onTriggered()
                tapCount = 0 // Reset
            }
        }
    }
}

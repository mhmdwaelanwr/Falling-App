package com.falling.anwar.ui.debug

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock

@Composable
fun Modifier.hiddenDebugTapTarget(
    tapsRequired: Int = 4,
    windowMs: Long = 3000,
    enabled: Boolean = true,
    onTriggered: () -> Unit
): Modifier {
    if (!enabled) return this

    var tapCount by remember { mutableStateOf(0) }
    var lastTapTime by remember { mutableStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }

    return this.clickable(
        interactionSource = interactionSource,
        indication = null // بدون تأثير بصري لإبقائه سرياً
    ) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        if (currentTime - lastTapTime > windowMs) {
            tapCount = 1
        } else {
            tapCount++
        }
        lastTapTime = currentTime

        if (tapCount >= tapsRequired) {
            onTriggered()
            tapCount = 0
        }
    }
}

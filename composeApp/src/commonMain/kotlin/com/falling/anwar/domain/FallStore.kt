package com.falling.anwar.domain

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock

class FallStore(
    private val settings: Settings = Settings(),
    private val alertExecutor: PlatformAlertExecutor,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private val _uiState = MutableStateFlow(
        try {
            FallUiState.valueOf(settings.getString(KEY_UI_STATE, FallUiState.NORMAL.name))
        } catch (e: Exception) {
            FallUiState.NORMAL
        }
    )
    val uiState: StateFlow<FallUiState> = _uiState.asStateFlow()

    private val _lastFallTimestamp = MutableStateFlow<Long?>(
        if (settings.hasKey(KEY_TIMESTAMP)) settings.getLong(KEY_TIMESTAMP, 0L) else null
    )
    val lastFallTimestamp: StateFlow<Long?> = _lastFallTimestamp.asStateFlow()

    init {
        if (_uiState.value == FallUiState.ALERT) {
            alertExecutor.startAlert()
        }
    }

    fun onEvent(event: FallEvent) {
        if (event == FallEvent.FALL) {
            val now = Clock.System.now().toEpochMilliseconds()
            _lastFallTimestamp.value = now
            settings.putLong(KEY_TIMESTAMP, now)

            if (_uiState.value != FallUiState.ALERT) {
                _uiState.value = FallUiState.ALERT
                settings.putString(KEY_UI_STATE, FallUiState.ALERT.name)
                alertExecutor.startAlert()
            }
        }
    }

    fun acknowledgeOk() {
        if (_uiState.value == FallUiState.ALERT) {
            _uiState.value = FallUiState.NORMAL
            settings.putString(KEY_UI_STATE, FallUiState.NORMAL.name)
            alertExecutor.stopAlert()
        }
    }

    companion object {
        private const val KEY_UI_STATE = "${AnwarSignature.PREF_PREFIX}ui_state"
        private const val KEY_TIMESTAMP = "${AnwarSignature.PREF_PREFIX}last_fall_ts"
    }
}

package com.falling.anwar.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MockFallEventSource : FallEventSource {
    private val _events = MutableSharedFlow<FallEvent>()
    
    override fun events(): Flow<FallEvent> = _events.asSharedFlow()

    suspend fun emitFall() {
        _events.emit(FallEvent.FALL)
    }

    suspend fun emitNoFall() {
        _events.emit(FallEvent.NO_FALL)
    }
}

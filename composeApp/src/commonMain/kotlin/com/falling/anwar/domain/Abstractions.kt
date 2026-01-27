package com.falling.anwar.domain

import kotlinx.coroutines.flow.Flow

interface FallEventSource {
    fun events(): Flow<FallEvent>
}

interface PlatformAlertExecutor {
    fun startAlert()
    fun stopAlert()
}

interface PlatformActions {
    fun callEmergency()
}

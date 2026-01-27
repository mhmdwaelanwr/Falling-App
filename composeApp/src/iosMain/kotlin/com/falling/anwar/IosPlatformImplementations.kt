package com.falling.anwar

import com.falling.anwar.domain.PlatformActions
import com.falling.anwar.domain.PlatformAlertExecutor

class IosPlatformAlertExecutor : PlatformAlertExecutor {
    override fun startAlert() {
        // TODO: Implement iOS local notification / alert
    }

    override fun stopAlert() {
        // TODO: Stop iOS alert
    }
}

class IosPlatformActions : PlatformActions {
    override fun callEmergency() {
        // TODO: Implement iOS dialer call
    }
}

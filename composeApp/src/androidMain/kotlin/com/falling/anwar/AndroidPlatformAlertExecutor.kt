package com.falling.anwar

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.falling.anwar.domain.PlatformAlertExecutor

class AndroidPlatformAlertExecutor(private val context: Context) : PlatformAlertExecutor {
    override fun startAlert() {
        val intent = Intent(context, AlertService::class.java)
        ContextCompat.startForegroundService(context, intent)
    }

    override fun stopAlert() {
        val intent = Intent(context, AlertService::class.java).apply {
            action = AlertService.ACTION_STOP
        }
        context.stopService(intent)
    }
}

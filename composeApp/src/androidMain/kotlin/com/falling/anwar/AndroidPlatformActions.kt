package com.falling.anwar

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.falling.anwar.domain.PlatformActions

class AndroidPlatformActions(private val context: Context) : PlatformActions {
    override fun callEmergency() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:911")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}

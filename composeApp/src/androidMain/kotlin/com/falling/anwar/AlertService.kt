package com.falling.anwar

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class AlertService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        if (action == ACTION_STOP) {
            stopSelf()
            return START_NOT_STICKY
        }

        startForeground(NOTIFICATION_ID, createNotification())
        startSiren()

        return START_STICKY
    }

    private fun startSiren() {
        if (mediaPlayer == null) {
            // Updated to use "alarm" as requested
            val resId = resources.getIdentifier("alarm", "raw", packageName)
            if (resId != 0) {
                mediaPlayer = MediaPlayer.create(this, resId)
            }
            
            if (mediaPlayer == null) {
                // Last resort fallback if alarm.mp3 is missing
                val alert = android.provider.Settings.System.DEFAULT_RINGTONE_URI
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(applicationContext, alert)
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                }
            }

            mediaPlayer?.apply {
                isLooping = true
                if (!isPlaying) {
                    try {
                        // Check if the mediaPlayer is already prepared (created from resource)
                        // If not, we might need to prepare it.
                        start()
                    } catch (e: Exception) {
                        try {
                            prepare()
                            start()
                        } catch (ex: Exception) {
                            // Handle potential prepare/start issues
                        }
                    }
                }
            }
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("FALL DETECTED")
            .setContentText("Emergency alert is active. Tap to open app.")
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Fall Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for fall detection events"
                enableLights(true)
                enableVibration(true)
                setImportance(NotificationManager.IMPORTANCE_HIGH)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        const val CHANNEL_ID = "fall_alerts"
        const val NOTIFICATION_ID = 1
        const val ACTION_STOP = "STOP_ALERT"
    }
}

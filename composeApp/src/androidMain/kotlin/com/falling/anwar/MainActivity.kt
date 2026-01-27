package com.falling.anwar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.falling.anwar.domain.FallStore
import com.falling.anwar.domain.MockFallEventSource
import com.falling.anwar.ui.App
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private lateinit var fallStore: FallStore
    private val mockEventSource = MockFallEventSource()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        checkPermissions()

        val alertExecutor = AndroidPlatformAlertExecutor(this)
        val platformActions = AndroidPlatformActions(this)
        fallStore = FallStore(Settings(), alertExecutor)

        mockEventSource.events()
            .onEach { fallStore.onEvent(it) }
            .launchIn(lifecycleScope)

        setContent {
            App(
                fallStore = fallStore,
                platformActions = platformActions,
                mockEventSource = mockEventSource
            )
        }
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

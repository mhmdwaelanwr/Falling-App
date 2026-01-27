package com.falling.anwar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.falling.anwar.isDebugBuild
import com.falling.anwar.ui.components.LastEventCard
import com.falling.anwar.ui.components.StatusBadge
import com.falling.anwar.ui.components.StatusChip
import com.falling.anwar.ui.debug.hiddenDebugTapTarget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    lastFallTimestamp: Long?,
    onOpenDebug: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Falling App",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.hiddenDebugTapTarget(
                            enabled = isDebugBuild,
                            onTriggered = onOpenDebug
                        )
                    )
                },
                actions = {
                    StatusChip("Monitoring")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically)
        ) {
            StatusBadge()

            LastEventCard(lastFallTimestamp)
            
            // Padding at bottom to balance the top bar
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

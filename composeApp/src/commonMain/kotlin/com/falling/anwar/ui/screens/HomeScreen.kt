package com.falling.anwar.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.falling.anwar.domain.AnwarSignature
import com.falling.anwar.ui.components.LastEventCard
import com.falling.anwar.ui.components.StatusBadge
import com.falling.anwar.ui.components.StatusChip
import com.falling.anwar.ui.debug.hiddenDebugTapTarget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    lastFallTimestamp: Long?,
    onSimulateFall: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "HomeAtmosphere")
    val auraScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "AuraScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF0FDF4),
                            Color.White
                        )
                    )
                )
        )
        
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopStart)
                .offset(x = (-100).dp, y = (-50).dp)
                .graphicsLayer {
                    scaleX = auraScale
                    scaleY = auraScale
                }
                .background(Color(0xFFDCFCE7).copy(alpha = 0.4f), CircleShape)
                .blur(80.dp)
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "FALLING APP",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1E293B),
                                letterSpacing = 2.sp
                            )
                        )
                    },
                    actions = {
                        StatusChip(
                            label = "STABLE",
                            modifier = Modifier.hiddenDebugTapTarget(
                                tapsRequired = 4,
                                onTriggered = onSimulateFall
                            )
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    StatusBadge(
                        status = "SAFE",
                        label = "Monitoring is active"
                    )
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    shadowElevation = 20.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(4.dp)
                                .background(Color(0xFFE2E8F0), CircleShape)
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            "CURRENT SYSTEM STATUS",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF64748B),
                                letterSpacing = 1.sp
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        LastEventCard(lastFallTimestamp)

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Arab Open University - Egypt",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF94A3B8),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

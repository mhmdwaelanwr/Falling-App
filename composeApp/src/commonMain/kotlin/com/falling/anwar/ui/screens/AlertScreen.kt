package com.falling.anwar.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.falling.anwar.ui.theme.AlertRed
import kotlinx.coroutines.delay
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun AlertScreen(
    timestamp: Long?,
    onAcknowledge: () -> Unit,
    onCallEmergency: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "HyperAlert")
    
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "IntensePulse"
    )

    val flashAlpha by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "FlashAlpha"
    )

    var secondsRemaining by remember { mutableStateOf(30) }
    LaunchedEffect(Unit) {
        while (secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
        }
    }

    val timeText = timestamp?.let {
        val instant = Instant.fromEpochMilliseconds(it)
        val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val month = dt.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
        "${dt.hour}:${dt.minute.toString().padStart(2, '0')} — ${dt.dayOfMonth} $month"
    } ?: "STAY CALM"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(AlertRed.copy(alpha = flashAlpha), Color.Transparent),
                        radius = 1500f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "EMERGENCY DETECTED",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.6f),
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Black
                )
            )
            
            Spacer(modifier = Modifier.height(30.dp))

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(320.dp)) {
                repeat(4) { index ->
                    val waveScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 3f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000, delayMillis = index * 500, easing = LinearOutSlowInEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "Wave$index"
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer {
                                scaleX = waveScale
                                scaleY = waveScale
                                alpha = (1f - (waveScale / 3f)).coerceIn(0f, 1f)
                            }
                            .border(1.5.dp, AlertRed.copy(alpha = 0.5f), CircleShape)
                    )
                }

                Surface(
                    modifier = Modifier
                        .size(190.dp)
                        .graphicsLayer {
                            scaleX = pulse
                            scaleY = pulse
                        },
                    color = AlertRed,
                    shape = CircleShape,
                    shadowElevation = 50.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFEF4444), Color(0xFF991B1B))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = secondsRemaining.toString(),
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Black
                                )
                            )
                            Text(
                                "SEC",
                                style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
                            )
                        }
                    }
                }
            }

            Text(
                "FALL DETECTED!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontSize = 42.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))
            
            Surface(
                color = AlertRed.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AlertRed.copy(alpha = 0.5f))
            ) {
                Text(
                    "EMERGENCY SIREN IS ACTIVE",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = AlertRed,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "DO YOU NEED ASSISTANCE?",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF1E293B),
                            fontWeight = FontWeight.Black
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = onAcknowledge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(85.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        shape = RoundedCornerShape(24.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            "I AM SAFE", 
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = onCallEmergency,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, AlertRed),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            "CALL EMERGENCY NOW", 
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = AlertRed
                            )
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        "Incident logged at $timeText",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

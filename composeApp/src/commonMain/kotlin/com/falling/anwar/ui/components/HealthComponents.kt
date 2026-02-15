package com.falling.anwar.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.falling.anwar.ui.theme.HealthcareTextSecondary
import com.falling.anwar.ui.theme.NormalGreen
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StatusBadge(
    modifier: Modifier = Modifier,
    status: String = "SAFE",
    label: String = "System monitoring active",
    reduceMotion: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "UltraNeuroAnimation")

    val pulse by if (reduceMotion) remember { mutableStateOf(1f) } else {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(2500, easing = EaseInOutQuart),
                repeatMode = RepeatMode.Reverse
            ),
            label = "OrganicPulse"
        )
    }

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "NeuralOrbit"
    )

    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowShimmer"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(320.dp)) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(260.dp)
                        .graphicsLayer {
                            val s = pulse * (1f + index * 0.15f)
                            scaleX = s
                            scaleY = s
                            alpha = (0.15f / (index + 1)) * shimmerAlpha
                            rotationZ = rotation * (index + 1) * 0.3f
                        }
                        .background(
                            Brush.radialGradient(
                                colors = listOf(NormalGreen, Color.Transparent),
                                radius = 400f
                            ),
                            CircleShape
                        )
                        .blur(70.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        scaleX = pulse
                        scaleY = pulse
                    },
                shape = CircleShape,
                color = Color.Transparent,
                shadowElevation = 40.dp,
                tonalElevation = 12.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    NormalGreen.lighten(0.2f),
                                    NormalGreen,
                                    NormalGreen.darken(0.15f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = status,
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 42.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.2f),
                                offset = Offset(0f, 4f),
                                blurRadius = 8f
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            color = Color(0xFFF0FDF4),
            shape = RoundedCornerShape(32.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, NormalGreen.copy(alpha = 0.2f)),
            modifier = Modifier.graphicsLayer {
                translationY = (pulse - 1f) * -15f 
            }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(NormalGreen, CircleShape)
                        .graphicsLayer { alpha = shimmerAlpha }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF166534)
                    )
                )
            }
        }
    }
}

@Composable
fun StatusChip(label: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFFF0FDF4),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, NormalGreen.copy(alpha = 0.3f)),
        modifier = modifier.padding(end = 16.dp)
    ) {
        Text(
            text = label.uppercase(),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Black,
                color = NormalGreen
            )
        )
    }
}

@Composable
fun LastEventCard(timestamp: Long?) {
    val timeText = if (timestamp != null) {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val month = dt.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
        "${dt.hour}:${dt.minute.toString().padStart(2, '0')} — ${dt.dayOfMonth} $month"
    } else {
        "ALL SYSTEMS OPTIMAL"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                color = Color(0xFFF8FAFC),
                shape = CircleShape,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("🛡️", fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "INTELLIGENT MONITORING",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    timeText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E293B)
                    )
                )
            }
        }
    }
}

fun Color.lighten(factor: Float) = Color(
    red = (red + (1f - red) * factor).coerceIn(0f, 1f),
    green = (green + (1f - green) * factor).coerceIn(0f, 1f),
    blue = (blue + (1f - blue) * factor).coerceIn(0f, 1f),
    alpha = alpha
)

fun Color.darken(factor: Float) = Color(
    red = (red * (1f - factor)).coerceIn(0f, 1f),
    green = (green * (1f - factor)).coerceIn(0f, 1f),
    blue = (blue * (1f - factor)).coerceIn(0f, 1f),
    alpha = alpha
)

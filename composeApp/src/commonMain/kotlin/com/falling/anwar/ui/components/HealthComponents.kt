package com.falling.anwar.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.falling.anwar.ui.theme.HealthcareTextSecondary
import com.falling.anwar.ui.theme.NormalGreen
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun StatusBadge(
    modifier: Modifier = Modifier,
    status: String = "NORMAL",
    label: String = "Monitoring active",
    reduceMotion: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulseScale by if (reduceMotion) {
        mutableStateOf(1f)
    } else {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.05f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .scale(pulseScale)
                .border(2.dp, NormalGreen.copy(alpha = 0.15f), CircleShape)
                .padding(12.dp)
                .background(NormalGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = status,
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = HealthcareTextSecondary
        )
    }
}

@Composable
fun StatusChip(label: String) {
    Surface(
        color = NormalGreen.copy(alpha = 0.08f),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(NormalGreen, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = NormalGreen,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LastEventCard(timestamp: Long?) {
    val timeText = if (timestamp != null) {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        "${dt.hour}:${dt.minute.toString().padStart(2, '0')}, ${dt.dayOfMonth} ${dt.month.name.take(3)}"
    } else {
        "None"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "LAST EVENT",
                style = MaterialTheme.typography.labelSmall,
                color = HealthcareTextSecondary,
                letterSpacing = androidx.compose.ui.unit.sp(1.2)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                timeText,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

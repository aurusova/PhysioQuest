package com.example.physioquest.screens.quiz.lernmodus

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.physioquest.HOME_SCREEN
import com.example.physioquest.R
import com.example.physioquest.model.QuizResult
import com.example.physioquest.ui.theme.md_theme_light_onSurfaceVariant
import com.example.physioquest.R.string as AppText

@Composable
fun ResultsScreen(
    result: QuizResult,
    openScreen: (String) -> Unit
) {
    val points = String.format("%.2f", result.scorePoints)
    val percent = String.format("%.2f", result.scorePercent)
    val total = result.totalPoints.toDouble()
    val progress = percent.toFloat()
    val angle = remember { Animatable(0f) }

    LaunchedEffect(progress) {
        angle.animateTo(
            targetValue = progress,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(AppText.results),
                style = MaterialTheme.typography.headlineSmall,
                color = md_theme_light_onSurfaceVariant,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Divider(modifier = Modifier.weight(1f))
        }
        Text(
            text = "Kategorie",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) { MedalBadge(angle.value) }
        Text(
            text = "$points / $total Punkte",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        Image(
            painter = painterResource(R.drawable.success),
            contentDescription = null
        )
        Button(
            onClick = { openScreen(HOME_SCREEN) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(AppText.finish),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(AppText.finish).uppercase())
        }
    }
}

@Composable
fun MedalBadge(angle: Float) {
    val sweepAngle by animateFloatAsState(targetValue = angle * 3.6f)
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(160.dp)) {
            val radius = size.minDimension / 2
            val strokeWidth = 40f
            val startColor = Color(0xFF9CD67D)
            val endColor = Color(0xFF58A28F)

            drawCircle(
                color = Color.LightGray,
                radius = radius,
                style = Stroke(strokeWidth)
            )

            drawArc(
                brush = Brush.verticalGradient(listOf(startColor, endColor)),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth)
            )
        }

        val percentage = String.format("%.2f", angle)
        Text(
            text = "${percentage}%",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )
    }
}
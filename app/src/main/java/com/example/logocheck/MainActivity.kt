package com.example.logocheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PushUpCounterApp()
        }
    }
}

@Composable
fun PushUpCounterApp() {
    var score by remember { mutableStateOf(0.0) }
    var lastTapTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var timeSinceLastTap by remember { mutableStateOf(0L) }

    LaunchedEffect(score) {
        while (true) {
            timeSinceLastTap = System.currentTimeMillis() - lastTapTime
            delay(50)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Score: ${score.roundToInt()}",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(Color(0xFF90CAF9), CircleShape)
                .clickable {
                    val now = System.currentTimeMillis()
                    val diff = (now - lastTapTime) / 1000.0
                    if (diff >= 0.10) {
                        score += (1.0 / diff)
                        lastTapTime = now
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PUSH",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Time since last push: ${(timeSinceLastTap / 1000.0).format(2)}s",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                score = 0.0
                lastTapTime = System.currentTimeMillis()
            }
        ) {
            Text("Reset")
        }
    }
}

// Helper extension to format Double
fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Preview(showBackground = true)
@Composable
fun PushUpCounterAppPreview() {
    PushUpCounterApp()
}
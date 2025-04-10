package com.tdavidc.dev.ui.screens.launcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.ui.theme.AppTheme

@Composable
fun LauncherScreen(modifier: Modifier = Modifier) {
    Surface {
        Box(
            contentAlignment = Alignment.Center, modifier = modifier
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome to Rovolut!",
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { }) {
                    Text("Continue")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 300, heightDp = 600)
@Composable
fun OnboardingPreview() {
    AppTheme {
        LauncherScreen()
    }
}
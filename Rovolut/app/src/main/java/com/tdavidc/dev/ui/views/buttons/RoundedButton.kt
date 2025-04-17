package com.tdavidc.dev.ui.views.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tdavidc.dev.ui.theme.AppTheme

@Composable
fun RoundedTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
        colors = ButtonDefaults.buttonColors()
            .copy(containerColor = containerColor, contentColor = contentColor)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Preview
@Composable
fun RoundedTextButtonPreview() {
    AppTheme {
        RoundedTextButton(
            onClick = {},
            text = "Login",
            containerColor = Color.Black
        )
    }
}
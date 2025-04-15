package com.tdavidc.dev.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.theme.AppTheme
import com.tdavidc.dev.ui.theme.black
import com.tdavidc.dev.ui.theme.dark_primary
import com.tdavidc.dev.ui.theme.light_primary
import com.tdavidc.dev.ui.theme.white

@Composable
fun CircleTextButton(
    text: String,
    onClick: () -> Unit,
    circleButtonSize: ButtonSize,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    val fontSize = when (circleButtonSize) {
        ButtonSize.Small -> 14.sp
        ButtonSize.Medium -> 18.sp
        ButtonSize.Large -> 22.sp
    }
    CircleButton(
        onClick = onClick,
        circleButtonSize = circleButtonSize,
        containerColor = containerColor,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun CircleImageButton(
    painter: Painter,
    onClick: () -> Unit,
    circleButtonSize: ButtonSize,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    val imageSize = when (circleButtonSize) {
        ButtonSize.Small -> 18.dp
        ButtonSize.Medium -> 24.dp
        ButtonSize.Large -> 30.dp
    }
    CircleButton(
        onClick = onClick,
        circleButtonSize = circleButtonSize,
        containerColor = containerColor,
        modifier = modifier
    ) {
        Image(
            painter,
            contentDescription = null,
            modifier = Modifier.size(imageSize),
            colorFilter = ColorFilter.tint(contentColor)
        )
    }
}

@Composable
internal fun CircleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    circleButtonSize: ButtonSize? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable (() -> Unit)
) {
    val newModifier = when (circleButtonSize) {
        ButtonSize.Small -> modifier.size(32.dp)

        ButtonSize.Medium -> modifier.size(50.dp)

        ButtonSize.Large -> modifier.size(68.dp)

        else -> modifier
    }
    Box(
        modifier = newModifier, contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.matchParentSize(),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = if (containerColor == Color.Transparent) 0.dp else 5.dp
            ),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = containerColor
            )
        ) {}
        content()
    }
}

enum class ButtonSize {
    Small, Medium, Large
}

@Preview
@Composable
fun CircleTextButtonPreview() {
    AppTheme {
        Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CircleTextButton(
                onClick = {},
                text = "1",
                containerColor = black,
                contentColor = white,
                circleButtonSize = ButtonSize.Large,
            )
            CircleTextButton(
                onClick = {},
                text = "1",
                circleButtonSize = ButtonSize.Medium,
            )
            CircleTextButton(
                onClick = {},
                text = "1",
                circleButtonSize = ButtonSize.Small,
                containerColor = Color.Transparent,
                contentColor = dark_primary
            )
        }
    }
}

@Preview
@Composable
fun CircleImageButtonPreview() {
    AppTheme {
        Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CircleImageButton(
                painter = painterResource(R.drawable.ic_payment),
                onClick = {},
                circleButtonSize = ButtonSize.Large,
                containerColor = black,
                contentColor = white
            )
            CircleImageButton(
                painter = painterResource(R.drawable.ic_payment),
                onClick = {},
                circleButtonSize = ButtonSize.Medium,
            )
            CircleImageButton(
                painter = painterResource(R.drawable.ic_payment),
                onClick = {},
                circleButtonSize = ButtonSize.Small,
                containerColor = Color.Transparent,
                contentColor = light_primary
            )
        }
    }
}
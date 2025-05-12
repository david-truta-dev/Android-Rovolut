package com.tdavidc.dev.ui.screens.authenticate.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.views.buttons.ButtonSize
import com.tdavidc.dev.ui.views.buttons.CircleImageButton

@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value,
        onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        trailingIcon = {
            if (value.isNotEmpty())
                CircleImageButton(
                    painterResource(R.drawable.ic_close),
                    { onValueChange("") },
                    circleButtonSize = ButtonSize.Small,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
        },
        placeholder = { Text(stringResource(R.string.phone_text_field_placeholder)) },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun PhoneTextFieldPreview() {
    PhoneTextField(
        "712345678",
        onValueChange = {}
    )
}
package com.tdavidc.dev.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.views.RoundedTextButton
import com.tdavidc.dev.ui.views.SetStatusBarStyle
import com.tdavidc.dev.ui.views.appbar.AppBarLazyScrollableScreen

@Composable
fun LoginScreen(
    onBackClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    SetStatusBarStyle(!isSystemInDarkTheme())
    Box(modifier = modifier) {
        AppBarLazyScrollableScreen(
            stringResource(R.string.login_title),
            onBackClicked = onBackClicked,
            trailingIcon = painterResource(R.drawable.ic_question),
            headerContent = {
                Text(
                    text = stringResource(R.string.login_description),
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
        ) {
            item {
                RoundedTextButton(
                    onLoginSuccess,
                    text = stringResource(R.string.login_continue_button),
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
            }
            items(1000) {
                Text(
                    "Lorem Ipsum lorem ipsum lorem",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(
    showBackground = true, showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        {},
        {}
    )
}
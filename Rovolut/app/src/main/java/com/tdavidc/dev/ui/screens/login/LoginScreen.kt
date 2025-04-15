package com.tdavidc.dev.ui.screens.login

import android.content.res.Configuration
import android.util.Log
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
import com.tdavidc.dev.ui.views.ButtonSize
import com.tdavidc.dev.ui.views.CircleImageButton
import com.tdavidc.dev.ui.views.CircleTextButton
import com.tdavidc.dev.ui.views.RoundedTextButton
import com.tdavidc.dev.ui.views.SetStatusBarStyle
import com.tdavidc.dev.ui.views.appbar.AppBarLazyScrollable

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
) {
    SetStatusBarStyle(!isSystemInDarkTheme())
    Box(modifier = modifier) {
        AppBarLazyScrollable(
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
            }
        ) {
            item {
                RoundedTextButton(
                    {
                        Log.d("DEBUGGING", "MyApp: RoundedTextButton")
                    },
                    text = stringResource(R.string.login_continue_button),
                    modifier = Modifier.padding(horizontal = 12.dp),
                )
            }
            item {
                CircleTextButton(
                    text = "btn",
                    onClick = {
                        Log.d("DEBUGGING", "MyApp: CircleTextButton")
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    circleButtonSize = ButtonSize.Medium
                )
            }
            item {
                CircleImageButton(
                    painter = painterResource(R.drawable.ic_card),
                    onClick = {
                        Log.d("DEBUGGING", "MyApp: CircleImageButton")
                    },
                    modifier = Modifier.padding(horizontal = 12.dp),
                    circleButtonSize = ButtonSize.Medium
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
    LoginScreen()
}
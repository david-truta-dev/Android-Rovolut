package com.tdavidc.dev.ui.screens.authenticate.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.screens.authenticate.views.CountryPrefixView
import com.tdavidc.dev.ui.screens.authenticate.views.PhoneTextField
import com.tdavidc.dev.ui.views.SetStatusBarStyle
import com.tdavidc.dev.ui.views.appbar.AppBarScrollableScreen
import com.tdavidc.dev.ui.views.buttons.RoundedTextButton

@Composable
fun LoginScreen(
    onBackClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
    onPhonePrefixClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    var phonePrefix by remember { mutableStateOf("+40") }
    var phoneFlag by remember { mutableIntStateOf(R.drawable.ic_flag_ro) }

    SetStatusBarStyle(!isSystemInDarkTheme())
    Box(modifier = modifier) {
        AppBarScrollableScreen(
            stringResource(R.string.login_title),
            onBackClicked = onBackClicked,
            trailingIcon = painterResource(R.drawable.ic_question),
            onTrailingButtonClicked = {
                Toast.makeText(
                    context,
                    context.getString(R.string.this_is_a_toast), Toast.LENGTH_SHORT
                ).show()
            },
            headerContent = {
                Text(
                    text = stringResource(R.string.login_description),
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CountryPrefixView(
                        painterResource(phoneFlag),
                        phonePrefix,
                        onPhonePrefixClicked,
                        modifier = Modifier.height(56.dp)
                    )
                    PhoneTextField(
                        phoneNumber,
                        onValueChange = { pn -> phoneNumber = pn },
                        modifier = Modifier.height(56.dp)
                    )
                }
                RoundedTextButton(
                    stringResource(R.string.login_continue_button),
                    onLoginSuccess,
                )
                Spacer(modifier = Modifier.height(12.dp))
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
        {},
        {}
    )
}
package com.tdavidc.dev.ui.screens.authenticate

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
import com.tdavidc.dev.ui.screens.authenticate.selectprefix.CountryPhonePrefix
import com.tdavidc.dev.ui.screens.authenticate.views.CountryPrefixView
import com.tdavidc.dev.ui.screens.authenticate.views.PhoneTextField
import com.tdavidc.dev.ui.views.SetStatusBarStyle
import com.tdavidc.dev.ui.views.appbar.AppBarScrollableScreen
import com.tdavidc.dev.ui.views.buttons.PlainTextButton
import com.tdavidc.dev.ui.views.buttons.RoundedTextButton

enum class LoginSignUpScreenMode {
    Login,
    SignUp
}

@Composable
fun LoginSignUpScreen(
    mode: LoginSignUpScreenMode,
    onBackClicked: () -> Unit,
    onPhonePrefixClicked: () -> Unit,
    phonePrefix: () -> CountryPhonePrefix,
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {},
    onAlreadyHaveAccountClicked: () -> Unit = {},
) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }
    val isLoginMode = mode == LoginSignUpScreenMode.Login

    SetStatusBarStyle(!isSystemInDarkTheme())
    Box(modifier = modifier) {
        AppBarScrollableScreen(
            stringResource(if (isLoginMode) R.string.login_title else R.string.register_title),
            onBackClicked = onBackClicked,
            trailingIcon = if (isLoginMode) painterResource(R.drawable.ic_question) else null,
            onTrailingButtonClicked = if (isLoginMode) {
                {
                    Toast.makeText(
                        context,
                        context.getString(R.string.this_is_a_toast), Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                {}
            },
            headerContent = {
                Text(
                    text = stringResource((if (isLoginMode) R.string.login_description else R.string.register_description)),
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
                        painterResource(phonePrefix().countryFlagResId),
                        phonePrefix().prefix,
                        onPhonePrefixClicked,
                        modifier = Modifier.height(56.dp)
                    )
                    PhoneTextField(
                        phoneNumber,
                        onValueChange = { pn -> phoneNumber = pn },
                        modifier = Modifier.height(56.dp)
                    )
                }
                if (!isLoginMode) PlainTextButton(
                    stringResource(R.string.register_already_have_account),
                    onAlreadyHaveAccountClicked
                )
                RoundedTextButton(
                    stringResource(R.string.login_continue_button),
                    if (isLoginMode) onLoginSuccess else onSignUpSuccess,
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
    LoginSignUpScreen(
        mode = LoginSignUpScreenMode.SignUp,
        {},
        {},
        { CountryPhonePrefix("+40", "Romania", R.drawable.ic_flag_ro) },
    )
}
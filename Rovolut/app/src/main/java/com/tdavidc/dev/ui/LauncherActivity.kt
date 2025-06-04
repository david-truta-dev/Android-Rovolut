package com.tdavidc.dev.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tdavidc.dev.R
import com.tdavidc.dev.navigation.AuthorizeDestination
import com.tdavidc.dev.navigation.CreateAccountDestination
import com.tdavidc.dev.navigation.HomeDestination
import com.tdavidc.dev.navigation.LauncherDestination
import com.tdavidc.dev.navigation.LoginDestination
import com.tdavidc.dev.navigation.PhonePrefixDestination
import com.tdavidc.dev.navigation.WelcomeDestination
import com.tdavidc.dev.ui.screens.authenticate.LoginSignUpScreen
import com.tdavidc.dev.ui.screens.authenticate.LoginSignUpScreenMode
import com.tdavidc.dev.ui.screens.authenticate.selectprefix.CountryPhonePrefix
import com.tdavidc.dev.ui.screens.authenticate.selectprefix.SelectPrefixScreen
import com.tdavidc.dev.ui.screens.authorize.AuthorizeScreen
import com.tdavidc.dev.ui.screens.main.MainScreen
import com.tdavidc.dev.ui.screens.welcome.WelcomeScreen
import com.tdavidc.dev.ui.theme.AppTheme
import com.tdavidc.dev.utility.extensions.navigateClearBackStackTo
import com.tdavidc.dev.utility.extensions.navigateSingleTopTo
import com.tdavidc.dev.utility.extensions.popBackStackOnce
import dagger.hilt.android.AndroidEntryPoint

// LauncherActivity is used only as the initial cold start of the application!
// Therefore, please DO NOT navigate back to it!
@AndroidEntryPoint
class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp(viewModel: LauncherViewModel = hiltViewModel()) {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LauncherDestination.route,
            ) {
                composable(route = LauncherDestination.route) {
                    val hasActiveSession by viewModel.hasActiveSession.observeAsState()

                    if (hasActiveSession != null)
                        LaunchedEffect(hasActiveSession) {
                            if (hasActiveSession == true) {
                                navController.navigateClearBackStackTo(AuthorizeDestination.route)
                            } else {
                                navController.navigateClearBackStackTo(WelcomeDestination.route)
                            }
                        }
                }
                composable(
                    route = WelcomeDestination.route,
                    enterTransition = { EnterTransition.None }) {
                    WelcomeScreen(
                        modifier = Modifier.systemBarsPadding(),
                        onClickCreateAccount = {
                            navController.navigateSingleTopTo(CreateAccountDestination.route)
                        },
                        onClickLogin = { navController.navigateSingleTopTo(LoginDestination.route) })
                }
                composable(route = CreateAccountDestination.route) { navBackStackEntry ->
                    val phonePrefix by navBackStackEntry
                        .savedStateHandle
                        .getLiveData<CountryPhonePrefix>("countryPrefix")
                        .observeAsState()

                    LoginSignUpScreen(
                        mode = LoginSignUpScreenMode.SignUp,
                        modifier = Modifier.systemBarsPadding(),
                        onBackClicked = {
                            navController.popBackStack(WelcomeDestination.route, false)
                        },
                        onSignUpSuccess = { navController.navigateClearBackStackTo(HomeDestination.route) },
                        onAlreadyHaveAccountClicked = {
                            navController.popBackStackOnce()
                            navController.navigateSingleTopTo(LoginDestination.route)
                        },
                        onPhonePrefixClicked = {
                            navController.navigateSingleTopTo(
                                PhonePrefixDestination.route
                            )
                        },
                        phonePrefix = {
                            phonePrefix ?: CountryPhonePrefix(
                                prefix = "+40",
                                countryName = "",
                                countryFlagResId = R.drawable.ic_flag_ro
                            )
                        }
                    )
                }
                composable(route = LoginDestination.route) { navBackStackEntry ->
                    val phonePrefix by navBackStackEntry
                        .savedStateHandle
                        .getLiveData<CountryPhonePrefix>("countryPrefix")
                        .observeAsState()

                    LoginSignUpScreen(
                        mode = LoginSignUpScreenMode.Login,
                        modifier = Modifier.systemBarsPadding(),
                        onBackClicked = {
                            navController.popBackStack(WelcomeDestination.route, false)
                        },
                        onLoginSuccess = { navController.navigateClearBackStackTo(HomeDestination.route) },
                        onPhonePrefixClicked = {
                            navController.navigateSingleTopTo(
                                PhonePrefixDestination.route
                            )
                        },
                        phonePrefix = {
                            phonePrefix ?: CountryPhonePrefix(
                                prefix = "+40",
                                countryName = "",
                                countryFlagResId = R.drawable.ic_flag_ro
                            )
                        }
                    )
                }
                composable(route = PhonePrefixDestination.route) {
                    SelectPrefixScreen(
                        modifier = Modifier.systemBarsPadding(),
                        onBackClicked = {
                            navController.popBackStackOnce()
                        },
                        onCountrySelected = {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                "countryPrefix",
                                it
                            )
                            navController.popBackStackOnce()
                        }
                    )
                }
                composable(route = AuthorizeDestination.route) {
                    AuthorizeScreen()
                }
                composable(route = HomeDestination.route) {
                    MainScreen(modifier = Modifier.systemBarsPadding())
                }
            }
        }
    }
}
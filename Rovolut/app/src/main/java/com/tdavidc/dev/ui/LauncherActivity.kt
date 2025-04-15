package com.tdavidc.dev.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tdavidc.dev.navigation.CreateAccountDestination
import com.tdavidc.dev.navigation.LauncherDestination
import com.tdavidc.dev.navigation.LoginDestination
import com.tdavidc.dev.navigation.WelcomeDestination
import com.tdavidc.dev.ui.screens.launcher.LauncherScreen
import com.tdavidc.dev.ui.screens.login.LoginScreen
import com.tdavidc.dev.ui.screens.welcome.WelcomeScreen
import com.tdavidc.dev.ui.theme.AppTheme
import com.tdavidc.dev.utility.extensions.navigateSingleTopTo
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
fun MyApp() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LauncherDestination.route,
            ) {
                composable(route = LauncherDestination.route) { LauncherScreen(navController) }
                composable(route = WelcomeDestination.route) {
                    WelcomeScreen(
                        modifier = Modifier.systemBarsPadding(),
                        onClickCreateAccount = {
                            Log.d("DEBUGGING", "MyApp: onClickCreateAccount")
                            navController.navigateSingleTopTo(LoginDestination.route) },
                        onClickLogin = { navController.navigateSingleTopTo(LoginDestination.route) })
                }
                composable(route = CreateAccountDestination.route) {
                    LoginScreen(modifier = Modifier.systemBarsPadding(), onBackClicked = {
                        Log.d("DEBUGGING", "MyApp: back clicked")
                        navController.popBackStack()
                    })
                }
                composable(route = LoginDestination.route) {
                    LoginScreen(modifier = Modifier.systemBarsPadding(), onBackClicked = {
                        Log.d("DEBUGGING", "MyApp: back clicked")
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}
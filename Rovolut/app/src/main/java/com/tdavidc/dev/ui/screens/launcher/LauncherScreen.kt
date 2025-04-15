package com.tdavidc.dev.ui.screens.launcher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tdavidc.dev.navigation.AuthorizeDestination
import com.tdavidc.dev.navigation.WelcomeDestination
import com.tdavidc.dev.utility.extensions.navigateClearBackStackTo

@Composable
fun LauncherScreen(
    navController: NavController,
    viewModel: LauncherViewModel = hiltViewModel()
) {
    val hasActiveSession by viewModel.hasActiveSession.observeAsState()

    if (hasActiveSession != null)
        LaunchedEffect(hasActiveSession) {
            if (hasActiveSession == true) {
                navController.navigateClearBackStackTo(AuthorizeDestination.route)
            } else {
                navController.navigateClearBackStackTo(WelcomeDestination.route)
            }
        }

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

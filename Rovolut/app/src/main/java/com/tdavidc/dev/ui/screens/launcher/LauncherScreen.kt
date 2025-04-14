package com.tdavidc.dev.ui.screens.launcher

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tdavidc.dev.ui.screens.welcome.WelcomeViewModel

@Composable
fun LauncherScreen(
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    Box(modifier = Modifier)
}

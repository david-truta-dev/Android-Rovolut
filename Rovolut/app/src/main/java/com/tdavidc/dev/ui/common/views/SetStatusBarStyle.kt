package com.tdavidc.dev.ui.common.views

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarStyle(useDarkIcons: Boolean) {
    val view = LocalView.current
    val window = (view.context as? Activity)?.window ?: return

    SideEffect {
        val controller = WindowInsetsControllerCompat(window, view)
        controller.isAppearanceLightStatusBars = useDarkIcons
    }
}

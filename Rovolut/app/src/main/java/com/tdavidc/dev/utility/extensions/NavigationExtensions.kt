package com.tdavidc.dev.utility.extensions

import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavController.navigateClearBackStackTo(route: String) =
    this.navigate(route) {
        popUpTo(0)
        launchSingleTop = true
    }

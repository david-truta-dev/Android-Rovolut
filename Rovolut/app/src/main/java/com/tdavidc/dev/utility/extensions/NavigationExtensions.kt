package com.tdavidc.dev.utility.extensions

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

fun NavController.navigateClearBackStackTo(route: String) =
    this.navigate(route) {
        popUpTo(0)
        launchSingleTop = true
    }

//This fixes the issue where double tapping a back button pops two entries instead of one
fun NavController.popBackStackOnce() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}

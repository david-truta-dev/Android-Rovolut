package com.tdavidc.dev.navigation

sealed interface Destination {
    val route: String
}

data object LauncherDestination : Destination {
    override val route = "launcher"
}

data object WelcomeDestination : Destination {
    override val route = "welcome"
}

data object AuthorizeDestination : Destination {
    override val route = "authorize"
}

data object LoginDestination : Destination {
    override val route = "login"
}

data object CreateAccountDestination : Destination {
    override val route = "createAccount"
}

data object PhonePrefixDestination : Destination {
    override val route = "phonePrefix"
}

data object HomeDestination : Destination {
    override val route = "home"
}

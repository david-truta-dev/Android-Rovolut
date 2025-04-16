package com.tdavidc.dev.navigation

interface Destination {
    val route: String
}

object LauncherDestination : Destination {
    override val route = "launcher"
}

object WelcomeDestination : Destination {
    override val route = "welcome"
}

object AuthorizeDestination : Destination {
    override val route = "authorize"
}

object LoginDestination : Destination {
    override val route = "login"
}

object CreateAccountDestination : Destination {
    override val route = "createAccount"
}

object HomeDestination : Destination {
    override val route = "createAccount"
}

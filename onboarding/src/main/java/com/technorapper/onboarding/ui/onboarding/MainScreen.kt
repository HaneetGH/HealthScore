package com.technorapper.onboarding.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.technorapper.onboarding.ui.nav.NAV_LOGIN
import com.technorapper.onboarding.ui.onboarding.compose.LoginCompose

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAV_LOGIN) {
        composable(NAV_LOGIN) { LoginCompose(navController) }
    }

}

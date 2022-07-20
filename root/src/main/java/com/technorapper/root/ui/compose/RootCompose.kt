package com.technorapper.root.ui.compose

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.technorapper.root.ui.list.ListActivityViewModel
import com.technorapper.root.ui.nav.NAV_ROOT_LIST

@ExperimentalMaterialApi

@Composable
fun RootCompose(listActivityViewModel: ListActivityViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAV_ROOT_LIST) {
        composable(NAV_ROOT_LIST) { LabsListCompose(
            navController,listActivityViewModel) }
    }

}
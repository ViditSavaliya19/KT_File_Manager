package com.example.filemanager.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavScreen(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "/")
        {
            composable("/")
            {
                HomeScreen(navController)
            }

        }

}
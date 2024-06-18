package com.example.filemanager.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.filemanager.ui.viewmodel.StorageViewModel

@Composable
fun NavScreen(
    navController: NavHostController,
    storageViewModel: StorageViewModel,
    context:Context
) {
        NavHost(navController = navController, startDestination = "/")
        {
            composable("/")
            {
                HomeScreen(navController,storageViewModel,context)
            }
            composable("Image")
            {
                ImageScreen(navController,storageViewModel,context)
            }

        }

}
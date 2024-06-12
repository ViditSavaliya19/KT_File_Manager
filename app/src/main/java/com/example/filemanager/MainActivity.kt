package com.example.filemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.filemanager.ui.screen.NavScreen
import com.example.filemanager.ui.theme.FileManagerTheme
import com.example.filemanager.ui.viewmodel.StorageViewModel
import com.example.filemanager.ui.viewmodel.StorageViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var storageViewModel: StorageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        storageViewModel = ViewModelProvider(this, StorageViewModelFactory(this))[StorageViewModel::class.java]

        setContent {
            val navController = rememberNavController()
            FileManagerTheme {
                NavScreen(navController,storageViewModel,this)
            }
        }

        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
               storageViewModel.permissionGranted.value =true
            } else {
                storageViewModel.permissionGranted.value =false
            }
        }
    }


}
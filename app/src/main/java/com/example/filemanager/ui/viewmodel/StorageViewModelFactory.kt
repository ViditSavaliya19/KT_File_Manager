package com.example.filemanager.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class StorageViewModelFactory(val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StorageViewModel::class.java))
        {
            return StorageViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
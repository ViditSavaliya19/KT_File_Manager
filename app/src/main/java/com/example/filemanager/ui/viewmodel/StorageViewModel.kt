package com.example.filemanager.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filemanager.ui.App
import com.example.filemanager.ui.domain.model.FolderModel
import com.example.filemanager.ui.domain.model.ImageModel
import com.example.filemanager.ui.domain.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StorageViewModel(val context: Context) : ViewModel() {
    var permissionGranted = MutableLiveData<Boolean>(false)
    val imageRepository = ImageRepository(context)
    private val _imageData = MutableStateFlow<List<FolderModel>>(
        emptyList()
    )
    val imageData: StateFlow<List<FolderModel>> = _imageData



    init {
        permissionGranted.value = checkPermissionStatus()
        if (permissionGranted.value == true) {
            loadPhotos()
        } else {
            requestPermission()
        }
    }

    fun loadPhotos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                imageRepository.getPhotosFlow(20).collect {
                    _imageData.value += it

                    Log.e("TAG", "loadPhotos: $it")
                }
            }

        }
    }

    private fun checkPermissionStatus(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            App.REQUEST_CODE_READ_EXTERNAL_STORAGE
        )


    }

}


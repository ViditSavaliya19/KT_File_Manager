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
import com.example.filemanager.ui.App
import com.example.filemanager.ui.domain.model.FolderModel
import com.example.filemanager.ui.domain.model.ImageModel
import com.example.filemanager.ui.domain.repository.ImageRepository

class StorageViewModel(val context: Context) : ViewModel() {
    var permissionGranted = MutableLiveData<Boolean>(false)
    val imageRepository = ImageRepository(context)
    private val  _imageData = MutableLiveData <List<FolderModel>>()
    val  imageData: LiveData<List<FolderModel>> = _imageData

    init {
        permissionGranted.value = checkPermissionStatus()
        permissionGranted.observeForever {
            if (it) {
                Log.e("ReadStorage", "imageRepository==============")
                _imageData.value = imageRepository.fetchImages()
                Log.e("ReadStorage", "imageRepository:  $imageData")
            } else {
                requestPermission()
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


package com.example.filemanager.ui.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
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
import com.example.filemanager.ui.domain.model.StorageModel
import com.example.filemanager.ui.domain.repository.FileManager
import com.example.filemanager.ui.domain.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StorageViewModel(val context: Context) : ViewModel() {
    var permissionGranted = MutableLiveData<Boolean>(false)
    val imageRepository = ImageRepository(context)


    private var _documentList = MutableLiveData<List<StorageModel>>()
    private var _audioList = MutableLiveData<List<StorageModel>>()
    private var _videoList = MutableLiveData<List<StorageModel>>()
    private var _noneList = MutableLiveData<List<StorageModel>>()
    private var _imageList = MutableLiveData<List<StorageModel>>()

    val documentList: LiveData<List<StorageModel>> = _documentList
    val audioList: LiveData<List<StorageModel>> = _audioList
    val videoList: LiveData<List<StorageModel>> = _videoList
    val noneList: LiveData<List<StorageModel>> = _noneList
    val imageList: LiveData<List<StorageModel>> = _imageList


    private val _imageData = MutableStateFlow<List<FolderModel>>(
        emptyList()
    )
    val imageData: StateFlow<List<FolderModel>> = _imageData


    init {
        permissionGranted.value = checkPermissionStatus()
        if (permissionGranted.value == true) {
//            loadPhotos()
            loadStorage()

        } else {
            requestPermission()
        }
    }

    fun loadPhotos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imageRepository.getPhotosFlow(20).collect {
                    _imageData.value += it

                    Log.e("TAG", "loadPhotos: $it")
                }
            }

        }
    }

    @SuppressLint("InlinedApi")
    fun loadStorage() {
        viewModelScope.launch {
            launch{
                Log.e("TAG", "loadStorage: Launch 1" )
                _audioList.value = FileManager.getInstance(context).fetchStorage(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO,
                )
            }
            launch {
                Log.e("TAG", "loadStorage: Launch 2" )
                _documentList.value = FileManager.getInstance(context).fetchStorage(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT,
                )
            }
            launch {
                Log.e("TAG", "loadStorage: Launch 3" )
                _videoList.value = FileManager.getInstance(context).fetchStorage(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
                )
            }
            launch {
                Log.e("TAG", "loadStorage: Launch 4" )
                _noneList.value = FileManager.getInstance(context).fetchStorage(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_NONE,
                )
            }
            launch {
                Log.e("TAG", "loadStorage: Launch 5" )
                _imageList.value = FileManager.getInstance(context).fetchStorage(
                    MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
                )
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


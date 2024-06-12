package com.example.filemanager.ui.domain.model

import android.net.Uri

data class FolderModel( val folderName: String, var documentList :MutableList<ImageModel> = emptyList<ImageModel>().toMutableList())
data class ImageModel(val id: Long, val name: String, val folderName: String, val uri: Uri)

package com.example.filemanager.ui.domain.model

data class StorageModel(
    val fileId: Long?=null,
    val fileName: String?= null,
    val filePath: String?=null,
    val bucketName: String?=null,
    val bucketID: String? =null,
    val mimeType: String?=null,
    val resolutions: String?=null,
    val size: String?=null,
    val title: String?=null,
    val dateAdded: String?=null,
    val dateModified: String? =null,
    val mediaType: String?=null,
)

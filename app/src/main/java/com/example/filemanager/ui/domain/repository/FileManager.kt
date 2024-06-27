package com.example.filemanager.ui.domain.repository

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.filemanager.ui.domain.model.DataModel
import com.example.filemanager.ui.domain.model.StorageModel
import okhttp3.MediaType


class FileManager(context: Context) {

    companion object {
        private const val TAG = "FileManager"

        @Volatile
        private var instance: FileManager? = null

        fun getInstance(context: Context): FileManager {
            if (instance == null) {
                synchronized(this) {
                    instance = FileManager(context.applicationContext)
                }
            }

            return instance!!
        }

    }

    val appContext: Context = context.applicationContext
    val contentResolver: ContentResolver = appContext.contentResolver

    val projection = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.DATA,
        MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Files.FileColumns.BUCKET_ID,
        MediaStore.Files.FileColumns.MIME_TYPE,
        MediaStore.Files.FileColumns.RESOLUTION,
        MediaStore.Files.FileColumns.SIZE,
        MediaStore.Files.FileColumns.TITLE,
        MediaStore.Files.FileColumns.DATE_ADDED,
        MediaStore.Files.FileColumns.DATE_MODIFIED,
        MediaStore.Files.FileColumns.MEDIA_TYPE,


        )

    val selection =
        MediaStore.Files.FileColumns.MEDIA_TYPE + " = ?" // Optional: Add a selection clause if needed
    val sortOrder = null // Optional: Specify sorting order


     fun fetchStorage(mediaType: Int): MutableMap<String, MutableList<DataModel>> {

        var dataList = mutableListOf<StorageModel>()
//        var documentList = mutableListOf<StorageModel>()
//        var audioList = mutableListOf<StorageModel>()
//        var videoList = mutableListOf<StorageModel>()
//        var noneList = mutableListOf<StorageModel>()
//        var imageList = mutableListOf<StorageModel>()


        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            arrayOf("${mediaType}"),
            sortOrder
        )

        var dataMap = mutableMapOf<String, MutableList<DataModel>>()

        cursor?.let {
            it.use { cursor ->
                while (cursor.moveToNext()) {
                    val fileId =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                    val fileName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                    val filePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                    val bucketName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME))
                    val bucketID =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID))
                    val mimeType =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE))
                    val resolution =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RESOLUTION))
                    val size =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE))
                    val title =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE))
                    val dateAdded =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_ADDED))
                    val dateModified =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED))
                    val mediaType =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE))

                    var model = DataModel(
                        title = title,
                        fileId = fileId,
                        fileName = fileName,
                        filePath = filePath,
                        size = size,
                        bucketID = bucketID,
                        mimeType = mimeType,
                        dateAdded = dateAdded,
                        dateModified = dateModified,
                        bucketName = bucketName,
                        resolutions = resolution,
                        mediaType = mediaType,
                    )


                    if (dataMap.containsKey(bucketName)) {
                        dataMap[bucketName]?.add(model)
                    } else {
                        if(bucketName!=null) {
                            dataMap[bucketName] = mutableListOf(model)
                        }
                    }

                }

            }
        }
        return dataMap
    }
}
/*
*
    fun fetchStorage() {
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            null,
            sortOrder
        )

        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val fileId =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                val fileName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                val filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                val bucketName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME))

                // Process the file information (e.g., display it in your UI)
                // ...

                Log.e(TAG, "fetchStorage: $bucketName $fileName $filePath")
            }
        }
    }
* */
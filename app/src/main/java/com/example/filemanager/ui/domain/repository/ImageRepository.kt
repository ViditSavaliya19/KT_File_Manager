package com.example.filemanager.ui.domain.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.filemanager.ui.domain.model.FolderModel
import com.example.filemanager.ui.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepository(val context: Context) {

    fun getPhotosFlow(pageSize: Int):Flow<List<FolderModel>> = flow {
        var offset=0

        while (true) {
            val folders = getFolderWithPhotos(offset,pageSize)
            if(folders.isEmpty()) break
            emit(folders)
            offset += pageSize
        }
    }

    fun getFolderWithPhotos(offset: Int, limit: Int): List<FolderModel> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC LIMIT $limit OFFSET $offset"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder
        )

        val folderMap = mutableMapOf<String, MutableList<ImageModel>>()

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val bucketColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val bucket = it.getString(bucketColumn)

                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                val image = ImageModel(id, name, bucket, uri)


                if (!folderMap.containsKey(bucket)) {
                    folderMap[bucket] = mutableListOf()
                }
                folderMap[bucket]?.add(image)
            }
        }

        return  folderMap.map { FolderModel(it.key, it.value) }
    }
}

package com.example.filemanager.ui.domain.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import com.example.filemanager.ui.domain.model.ImageModel

class ImageRepository(private val contentResolver: ContentResolver) {

    fun fetchImages(): Map<String, List<ImageModel>> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        val imageFolders = mutableMapOf<String, MutableList<ImageModel>>()

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val bucketColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val bucket = it.getString(bucketColumn)

                val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                val image = ImageModel(id, name, bucket, uri)

                if (!imageFolders.containsKey(bucket)) {
                    imageFolders[bucket] = mutableListOf()
                }
                imageFolders[bucket]?.add(image)
            }
        }

        return imageFolders
    }
}

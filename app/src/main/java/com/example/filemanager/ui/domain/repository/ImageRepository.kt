package com.example.filemanager.ui.domain.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.filemanager.ui.domain.model.FolderModel
import com.example.filemanager.ui.domain.model.ImageModel

class ImageRepository(val context: Context) {

    fun fetchImages(): MutableList<FolderModel> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder
        )

//        val imageFolders = mutableMapOf<String, MutableList<ImageModel>>()
        val imageFolders = mutableListOf<FolderModel>()

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


                var folder = imageFolders.find {
                    it.folderName == bucket
                }

                if (folder == null) {
                    imageFolders.add(FolderModel(bucket))
                }
//                if (!imageFolders.containsKey(bucket)) {
//                    imageFolders[bucket] = mutableListOf()
//                }
                val bucketIndex = imageFolders.indexOf(folder)

                try {
                    Log.e("TAG", "fetchImages: $bucketIndex $bucket $imageFolders $folder")
                    imageFolders[bucketIndex].documentList!!.add(image)

                }catch (e:Exception)
                {
                    Log.e("Error", "fetchImages: ====== $bucketIndex", )
                }

            }
        }

        return imageFolders
    }
}

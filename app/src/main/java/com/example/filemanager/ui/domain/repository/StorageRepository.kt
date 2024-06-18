package com.example.filemanager.ui.domain.repository

import android.os.Environment
import java.io.File

class StorageRepository {
    private fun readFiles() {
        val storageDir = Environment.getExternalStorageDirectory()
        listFiles(storageDir)
    }

    private fun listFiles(directory: File) {
        val files = directory.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    // If the file is a directory, recursively call listFiles
                    listFiles(file)
                } else {
                    // Process the file (e.g., display its name)
                    println("File: ${file.absolutePath}")
                }
            }
        }
    }

}
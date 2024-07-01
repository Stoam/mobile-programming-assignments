package com.example.tugas45678.util

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class UriManager {
    companion object{
        fun convertContentToFilesUri(context: Context, contentUri: Uri): Uri {
            val contentBytes = context.contentResolver.openInputStream(contentUri)?.use {
                it.readBytes()
            }

            val internalFile = File(context.filesDir, contentUri.lastPathSegment!! + ".jpg")
            FileOutputStream(internalFile).use {
                it.write(contentBytes)
            }

            return internalFile.toUri()
        }

        fun getFileFromUri(context: Context, uri: Uri): File {

            val inputStream = context.contentResolver.openInputStream(uri)

            val file = File(context.cacheDir, uri.lastPathSegment!!)
            file.createNewFile()
            file.outputStream().use {
                inputStream!!.copyTo(it)
            }

            return file
        }
    }
}
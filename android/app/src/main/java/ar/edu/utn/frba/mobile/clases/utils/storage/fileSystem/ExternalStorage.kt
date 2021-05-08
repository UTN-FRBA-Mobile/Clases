package ar.edu.utn.frba.mobile.clases.utils.storage.fileSystem

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ExternalStorage {
    companion object {
        fun getFileUri(context: Context, fileName: String): String? {
            val file = File(getAppFolder(context) + fileName)
            if (file.exists()) {
                return file.toURI().toString()
            }
            return null
        }

        fun saveFile(context: Context, bitmap: Bitmap, fileName: String) : File {
            val file = File(getAppFolder(context) + fileName + ".JPEG")
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream)
                ostream.flush()
                ostream.close()
            } catch (e: IOException) {
                Log.e("IOException", e.localizedMessage)
            }

            return file
        }

        fun deleteFile(context: Context, fileName: String) {
            val file = File(getAppFolder(context) + fileName)
            if (file.exists()) {
                file.delete()
            }
        }

        fun getCacheFileUri(context: Context, fileName: String): String? {
            val file = File(context.externalCacheDir!!.path + fileName)
            if (file.exists()) {
                return file.toURI().toString()
            }
            return null
        }

        fun saveFileInCache(context: Context, bitmap: Bitmap, fileName: String) : File {
            val file = File(context.externalCacheDir!!.path + fileName)
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream)
                ostream.flush()
                ostream.close()
            } catch (e: IOException) {
                Log.e("IOException", e.localizedMessage)
            }

            return file
        }

        fun deleteFileFromCache(context: Context, fileName: String) {
            val file = File(context.externalCacheDir!!.path + fileName)
            if (file.exists()) {
                file.delete()
            }
        }

        fun getFiles(context: Context): Array<out File>? {
            val appFolder = getAppFolder(context)
            val directory = File(appFolder)
            return directory.listFiles()
        }

        private fun getAppFolder(context: Context): String {
            val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.path + "/clases/"
            val folder = File(path)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            return path
        }
    }
}
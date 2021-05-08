package ar.edu.utn.frba.mobile.clases.utils.storage.fileSystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

class ExternalContent {

    companion object {

        fun getBitmapFromGallery(context: Context, path: Uri, width: Int, height: Int): Bitmap {
            val fd = context.contentResolver.openAssetFileDescriptor(path, "r")

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFileDescriptor(fd!!.fileDescriptor, null, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, width, height)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            val image = BitmapFactory.decodeFileDescriptor(fd.fileDescriptor, null, options)
            fd.close()
            return image
        }

        private fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
        ): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }
    }
}

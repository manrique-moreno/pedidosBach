package com.example.pedidosbach.infraestructure.connect.product

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpImageConnect {
    private val mStorageRef = FirebaseStorage.getInstance().getReference("Productos")

    suspend fun upImage(mImageUri: Uri?, context: Context): StorageTask<UploadTask.TaskSnapshot> {
        return withContext(Dispatchers.IO){
            val fileReference = mStorageRef.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri!!, context)
            )
            fileReference.putFile(mImageUri)
        }
    }

    private fun getFileExtension(uri: Uri, context: Context): String? {
        val cR = context.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
}
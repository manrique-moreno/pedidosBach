package com.example.pedidosbach.aplication.useCase.product

import android.content.Context
import android.net.Uri
import com.example.pedidosbach.infraestructure.connect.product.UpImageConnect
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask

class UpImageUseCase {
    private val connect = UpImageConnect()

    suspend fun upImage(mImageUri: Uri?, context: Context) : StorageTask<UploadTask.TaskSnapshot> {
        return connect.upImage(mImageUri, context)
    }
}
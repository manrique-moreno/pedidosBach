package com.example.pedidosbach.presentation.viewModel.product

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.aplication.useCase.product.UpImageUseCase
import com.example.pedidosbach.domain.model.product.UpImageResponse
import com.example.pedidosbach.domain.enums.EnumImage
import kotlinx.coroutines.launch

class UpImageViewModel : ViewModel() {
    val response = MutableLiveData<UpImageResponse?>()
    val messageToast = MutableLiveData<String>()
    val messageText = MutableLiveData<String>()
    private val upImageUC = UpImageUseCase()

    fun onCreate(mImageUri: Uri?, context: Context) {
        response.postValue(UpImageResponse(EnumImage.isloading, "subiendo imagen..."))

        viewModelScope.launch {
            upImageUC.upImage(mImageUri, context)
                .addOnSuccessListener {
                    response.postValue(UpImageResponse(EnumImage.isSuccessful, ""))
                    messageText.postValue("imagen subida...")
                }
                .addOnFailureListener { e ->
                    response.postValue(UpImageResponse(EnumImage.isError, ""))
                    messageToast.postValue("error: ${e.message}")
                }
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    task.result.storage.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        response.postValue(
                            UpImageResponse(
                                EnumImage.isComplete, task.result.toString()
                            )
                        )
                        messageText.postValue("recuperando la url de la imagen...")
                    } else {
                        response.postValue(UpImageResponse(EnumImage.isError, ""))
                        messageToast.postValue("error: ${task.exception?.message}")
                    }
                }
        }
    }

}
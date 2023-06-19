package com.example.pedidosbach.presentation.viewModel.product

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.aplication.useCase.product.AddProductUseCase
import com.example.pedidosbach.domain.enums.EnumAddProduct

import kotlinx.coroutines.launch

class AddProductViewModel : ViewModel() {
    val response = MutableLiveData<EnumAddProduct>()
    val message = MutableLiveData<String>()
    private val upImageUC = AddProductUseCase()

    fun onCreate(
        txtName: EditText,
        image: String,
        category: String,
        txtPrice: EditText,
        txtDescription: EditText
    ) {
        val name = txtName.text.toString().trim { it <= ' ' }
        val price = txtPrice.text.toString().trim { it <= ' ' }
        val description = txtDescription.text.toString().trim { it <= ' ' }
        response.postValue(EnumAddProduct.isloading)

        viewModelScope.launch {
            upImageUC.addProduct(name, image, category, price, description)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        response.postValue(EnumAddProduct.isSuccessful)
                        message.postValue("Producto registrado")
                    } else {
                        response.postValue(EnumAddProduct.isError)
                        message.postValue("error: ${task.exception?.message}")
                    }
                }
        }
    }

}
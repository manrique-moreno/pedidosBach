package com.example.pedidosbach.presentation.viewModel.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.aplication.listener.IProductLoadListener
import com.example.pedidosbach.aplication.useCase.product.LoadProductUseCase
import kotlinx.coroutines.launch

class LoadProductViewModel :  ViewModel() {
    private val productUC = LoadProductUseCase()

    fun onCreate(productLoadListener: IProductLoadListener) {
        viewModelScope.launch {
            productUC.loadProduct(productLoadListener)
        }
    }


}
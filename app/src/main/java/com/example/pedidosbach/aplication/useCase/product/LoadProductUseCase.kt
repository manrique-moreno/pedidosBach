package com.example.pedidosbach.aplication.useCase.product

import com.example.pedidosbach.aplication.listener.IProductLoadListener
import com.example.pedidosbach.infraestructure.connect.product.LoadProductConnect

class LoadProductUseCase {
    private val connect = LoadProductConnect()

    suspend fun loadProduct(productLoadListener: IProductLoadListener) {
        return connect.loadProducts(productLoadListener)
    }
}
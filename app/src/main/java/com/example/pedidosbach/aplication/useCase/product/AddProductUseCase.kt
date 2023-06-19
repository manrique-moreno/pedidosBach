package com.example.pedidosbach.aplication.useCase.product

import com.example.pedidosbach.domain.model.product.ProductRequest
import com.example.pedidosbach.infraestructure.connect.product.AddProductConnect
import com.google.android.gms.tasks.Task

class AddProductUseCase {
    private val connect = AddProductConnect()

    suspend fun addProduct(
        name: String,
        image: String,
        category: String,
        price: String,
        description: String
    ): Task<Void> {
        return connect.addProduct(
            ProductRequest(
                name = name,
                image = image,
                category = category,
                price = price,
                descripcion = description
            )
        )
    }

}
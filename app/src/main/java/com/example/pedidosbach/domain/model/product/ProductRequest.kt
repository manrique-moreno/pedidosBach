package com.example.pedidosbach.domain.model.product

import com.google.firebase.database.Exclude

data class ProductRequest(
    var name: String? = null,
    var image: String? = null,
    @get:Exclude
    @set:Exclude
    var key: String? = null,
    var category: String? = null,
    var price: String? = null,
    var descripcion: String? = null
)

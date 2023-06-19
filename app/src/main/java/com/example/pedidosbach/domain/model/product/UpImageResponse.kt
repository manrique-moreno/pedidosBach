package com.example.pedidosbach.domain.model.product

import com.example.pedidosbach.domain.enums.EnumImage

data class UpImageResponse(
    val enumImage: EnumImage,
    val urlImage: String
)

package com.example.pedidosbach.domain.model

data class PedidoRequest(
    val idUser: String? = null,
    val Name: String? = null,
    val direccion: String? = null,
    val fecha: String? = null,
    val hora: String? = null,
    val Total: String? = null,
    val Metodo: String? = null,
    val Estado: String? = null,
    val productos: MutableList<CartModel>
)

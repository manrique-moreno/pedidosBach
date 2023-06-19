package com.example.pedidosbach.domain.model

class PedidoModel {
    var key: String? = null
    val idUser: String? = null
    var name: String? = null
    var direccion: String? = null
    var fecha: String? = null
    val hora: String? = null
    var estado: String? = null
    var metodo: String? = null
    var total: String? = null
    val productos: MutableList<CartModel>? = null

}
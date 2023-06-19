package com.example.pedidosbach.aplication.listener

import com.example.pedidosbach.domain.model.PedidoModel

interface IPedidoLoadListener {
    fun onPedidoLoadLoading()
    fun onPedidoLoadSuccess(pedidoModelList:MutableList<PedidoModel>)
    fun onPedidoLoadNot(message: String)
    fun onPedidoLoadFailed(message: String?)
}
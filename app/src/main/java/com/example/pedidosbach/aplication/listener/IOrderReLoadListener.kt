package com.example.pedidosbach.aplication.listener

import com.example.pedidosbach.domain.model.PedidoModel

interface IOrderReLoadListener {
    fun onOrderReLoadSuccess(position: Int, pedidoModelList: MutableList<PedidoModel>)
    fun onReLoadNotOrder(position: Int, pedidoModelList: MutableList<PedidoModel>, message: String)
    fun onOrderReLoadFailed(message: String)
}
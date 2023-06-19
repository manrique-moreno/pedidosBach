package com.example.pedidosbach.aplication.useCase

import com.example.pedidosbach.aplication.listener.IOrderReLoadListener
import com.example.pedidosbach.aplication.listener.IOrderUpdateListener
import com.example.pedidosbach.aplication.listener.IPayOrderListener
import com.example.pedidosbach.aplication.listener.IPedidoLoadListener
import com.example.pedidosbach.domain.model.PedidoModel
import com.example.pedidosbach.domain.model.PedidoRequest
import com.example.pedidosbach.infraestructure.connect.order.OrderConnect

class OrderUseCase {
    private val connect = OrderConnect()

    suspend fun payOrder(order: PedidoRequest, iPayOrderListener: IPayOrderListener){
        return connect.payOrderFirebase(order, iPayOrderListener)
    }

    suspend fun loadOrders(iPedidoLoadListener: IPedidoLoadListener){
        return connect.loadOrdersFromFirebase(iPedidoLoadListener)
    }

    suspend fun updateOrder(pos: Int, orderItem: PedidoModel, iOrderUpdateListener: IOrderUpdateListener){
        return connect.ordersUpdateFromFirebase(pos, orderItem, iOrderUpdateListener)
    }

    suspend fun reLoadOrder(pos: Int, iOrderReLoadListener: IOrderReLoadListener){
        return connect.orderReLoadFromFirebase(pos, iOrderReLoadListener)
    }
}
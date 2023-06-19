package com.example.pedidosbach.presentation.viewModel.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.aplication.listener.IOrderReLoadListener
import com.example.pedidosbach.aplication.listener.IOrderUpdateListener
import com.example.pedidosbach.aplication.listener.IPayOrderListener
import com.example.pedidosbach.aplication.listener.IPedidoLoadListener
import com.example.pedidosbach.aplication.useCase.OrderUseCase
import com.example.pedidosbach.domain.model.PedidoModel
import com.example.pedidosbach.domain.model.PedidoRequest
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    private val orderUC = OrderUseCase()

    fun payOrder(order: PedidoRequest, iPayOrderListener: IPayOrderListener){
        viewModelScope.launch {
            orderUC.payOrder(order, iPayOrderListener)
        }
    }

    fun loadOrder(iPedidoLoadListener: IPedidoLoadListener){
        viewModelScope.launch{
            orderUC.loadOrders(iPedidoLoadListener)
        }
    }

    fun updateOrder(pos: Int, orderItem: PedidoModel, iOrderUpdateListener: IOrderUpdateListener){
        viewModelScope.launch {
            orderUC.updateOrder(pos, orderItem, iOrderUpdateListener)
        }
    }

    fun reLoadOrders(pos: Int, iOrderReLoadListener: IOrderReLoadListener){
        viewModelScope.launch{
            orderUC.reLoadOrder(pos, iOrderReLoadListener)
        }
    }
}
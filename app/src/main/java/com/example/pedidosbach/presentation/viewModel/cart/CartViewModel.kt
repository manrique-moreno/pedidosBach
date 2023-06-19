package com.example.pedidosbach.presentation.viewModel.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.presentation.listener.ICartCountListener
import com.example.pedidosbach.presentation.listener.ICartAddListener
import com.example.pedidosbach.presentation.listener.ICartDeleteAllListener
import com.example.pedidosbach.presentation.listener.ICartDeleteItemListener
import com.example.pedidosbach.presentation.listener.ICartLoadListener
import com.example.pedidosbach.presentation.listener.ICartReLoadListener
import com.example.pedidosbach.presentation.listener.ICartUpdateListener
import com.example.pedidosbach.aplication.useCase.cart.CartUseCase
import com.example.pedidosbach.domain.model.CartModel
import com.example.pedidosbach.domain.model.product.ProductResponse
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val cartUC = CartUseCase()

    fun count(cartLoadListener: ICartCountListener) {
        viewModelScope.launch {
            cartUC.countCart(cartLoadListener)
        }
    }

    fun add(productResponse: ProductResponse, cartAddListener: ICartAddListener){
        viewModelScope.launch{
            cartUC.addCart(productResponse, cartAddListener)
        }
    }

    fun load(cartLoadListener: ICartLoadListener){
        viewModelScope.launch {
            cartUC.loadCart(cartLoadListener)
        }
    }

    fun reLoad(cartReLoadListener: ICartReLoadListener){
        viewModelScope.launch {
            cartUC.reLoadCart(cartReLoadListener)
        }
    }

    fun update(cartModel: CartModel, iCartUpdateListener: ICartUpdateListener){
        viewModelScope.launch {
            cartUC.updateCart(cartModel, iCartUpdateListener)
        }
    }

    fun deleteItemCart(position: Int, cart: CartModel, iCartDeleteItemListener: ICartDeleteItemListener){
        viewModelScope.launch{
            cartUC.deleteItemCart(position, cart, iCartDeleteItemListener)
        }
    }

    fun deleteAllCart(iCartDeleteAllListener: ICartDeleteAllListener){
        viewModelScope.launch {
            cartUC.deleteAllCart(iCartDeleteAllListener)
        }
    }
}
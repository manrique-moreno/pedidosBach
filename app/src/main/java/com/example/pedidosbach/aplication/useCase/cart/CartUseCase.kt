package com.example.pedidosbach.aplication.useCase.cart

import com.example.pedidosbach.presentation.listener.ICartCountListener
import com.example.pedidosbach.presentation.listener.ICartAddListener
import com.example.pedidosbach.presentation.listener.ICartDeleteAllListener
import com.example.pedidosbach.presentation.listener.ICartDeleteItemListener
import com.example.pedidosbach.presentation.listener.ICartLoadListener
import com.example.pedidosbach.presentation.listener.ICartReLoadListener
import com.example.pedidosbach.presentation.listener.ICartUpdateListener
import com.example.pedidosbach.domain.model.CartModel
import com.example.pedidosbach.domain.model.product.ProductResponse
import com.example.pedidosbach.infraestructure.connect.cart.CartConnect

class CartUseCase {
    private val connect = CartConnect()

    suspend fun countCart(cartCountListener: ICartCountListener) {
        connect.countFirebase(cartCountListener)
    }

    suspend fun addCart(productResponse: ProductResponse, cartAddListener: ICartAddListener) {
        connect.addToCart(productResponse, cartAddListener)
    }

    suspend fun loadCart(cartLoadListener: ICartLoadListener) {
        connect.loadCart(cartLoadListener)
    }

    suspend fun reLoadCart(cartReLoadListener: ICartReLoadListener){
        connect.reLoadCart(cartReLoadListener)
    }

    suspend fun updateCart(cartModel: CartModel, iCartUpdateListener: ICartUpdateListener) {
        connect.update(cartModel, iCartUpdateListener)
    }

    suspend fun deleteItemCart(position: Int, cart: CartModel, iCartDeleteItemListener: ICartDeleteItemListener){
        connect.deleteItem(position, cart, iCartDeleteItemListener)
    }

    suspend fun deleteAllCart(iCartDeleteAllListener: ICartDeleteAllListener){
        connect.deleteCart(iCartDeleteAllListener)
    }
}
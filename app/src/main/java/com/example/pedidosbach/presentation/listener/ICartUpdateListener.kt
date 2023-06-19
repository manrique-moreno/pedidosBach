package com.example.pedidosbach.presentation.listener

import com.example.pedidosbach.domain.model.CartModel

interface ICartUpdateListener {
    fun onUpdateCartSuccess(message:String)
    fun onUpdateCartFailed(message:String)
}
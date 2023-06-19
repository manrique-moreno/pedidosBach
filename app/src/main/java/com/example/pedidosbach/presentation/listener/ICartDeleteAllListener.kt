package com.example.pedidosbach.presentation.listener


interface ICartDeleteAllListener {
    fun onDeleteAllCartSuccess(message:String)
    fun onDeleteAllCartFailed(message:String)
}
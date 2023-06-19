package com.example.pedidosbach.aplication.listener


interface ICartDeleteAllListener {
    fun onDeleteAllCartSuccess(message:String)
    fun onDeleteAllCartFailed(message:String)
}
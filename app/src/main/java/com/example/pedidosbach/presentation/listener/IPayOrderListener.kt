package com.example.pedidosbach.presentation.listener


interface IPayOrderListener {
    fun onPayOrderSuccess(message:String)
    fun onPayOrderFailed(message:String)
}
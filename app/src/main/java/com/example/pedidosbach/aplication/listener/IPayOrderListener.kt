package com.example.pedidosbach.aplication.listener


interface IPayOrderListener {
    fun onPayOrderSuccess(message:String)
    fun onPayOrderFailed(message:String)
}
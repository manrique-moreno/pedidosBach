package com.example.pedidosbach.aplication.listener

import com.example.pedidosbach.domain.model.product.ProductResponse

interface IProductLoadListener {
    fun onProductLoading()
    fun onProductLoadSuccess(productResponseList:MutableList<ProductResponse>)
    fun onNotProduct()
    fun onProductLoadFailed(message:String?)

}
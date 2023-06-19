package com.example.pedidosbach.infraestructure.connect.product


import android.util.Log
import com.example.pedidosbach.aplication.listener.IProductLoadListener
import com.example.pedidosbach.domain.model.product.ProductResponse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadProductConnect {
    private val mDatabaseRef = FirebaseDatabase.getInstance().getReference("Producto")

    suspend fun loadProducts(productLoadListener: IProductLoadListener) {
        val productResponses: MutableList<ProductResponse> = ArrayList()
        productLoadListener.onProductLoading()
        return withContext(Dispatchers.IO) {
            mDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (product in snapshot.children) {
                            val productResponse = product.getValue(ProductResponse::class.java)
                            if (productResponse != null) {
                                productResponse.key = product.key.toString()
                                productResponses.add(productResponse)
                            }
                        }
                        productLoadListener.onProductLoadSuccess(productResponses)
                    } else {
                        productLoadListener.onNotProduct()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    productLoadListener.onProductLoadFailed("No hay conexion")
                    Log.d("noresponse",error.message)
                }

            })
        }
    }

}
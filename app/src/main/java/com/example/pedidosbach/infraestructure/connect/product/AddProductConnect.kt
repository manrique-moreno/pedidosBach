package com.example.pedidosbach.infraestructure.connect.product

import com.example.pedidosbach.domain.model.product.ProductRequest
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddProductConnect {
    private val mDatabaseRef = FirebaseDatabase.getInstance().getReference("Producto")

    suspend fun addProduct( product: ProductRequest): Task<Void> {
        return withContext(Dispatchers.IO){
            val uploadId = mDatabaseRef.push().key
            mDatabaseRef.child((uploadId)!!).setValue(product)
        }
    }
}
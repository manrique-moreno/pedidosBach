package com.example.pedidosbach.infraestructure.connect.order

import com.example.pedidosbach.aplication.listener.IOrderReLoadListener
import com.example.pedidosbach.aplication.listener.IOrderUpdateListener
import com.example.pedidosbach.aplication.listener.IPayOrderListener
import com.example.pedidosbach.aplication.listener.IPedidoLoadListener
import com.example.pedidosbach.domain.model.PedidoModel
import com.example.pedidosbach.domain.model.PedidoRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderConnect {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("Pedidos")

    suspend fun payOrderFirebase(order: PedidoRequest, iPayOrderListener: IPayOrderListener) {
        return withContext(Dispatchers.IO) {
            val pedidoId = databaseReference.push().key
            databaseReference.child(pedidoId!!).setValue(order)
                .addOnSuccessListener {
                    iPayOrderListener.onPayOrderSuccess("Tu pedido ha sido realizado")
                }.addOnFailureListener {
                    iPayOrderListener.onPayOrderFailed("Pago Rechazado")
                }
        }
    }

    suspend fun loadOrdersFromFirebase(iPedidoLoadListener: IPedidoLoadListener) {
        val pedidoModels: MutableList<PedidoModel> = ArrayList()
        iPedidoLoadListener.onPedidoLoadLoading()
        return withContext(Dispatchers.IO) {
            databaseReference.orderByChild("estado").equalTo("Pendiente")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (pedidoSnapshot in snapshot.children) {
                                val pedidoModel = pedidoSnapshot.getValue(PedidoModel::class.java)
                                pedidoModel!!.key = pedidoSnapshot.key
                                pedidoModels.add(pedidoModel)
                            }
                            iPedidoLoadListener.onPedidoLoadSuccess(pedidoModels)

                        } else {
                            iPedidoLoadListener.onPedidoLoadNot("No hay pedidos pendientes")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        iPedidoLoadListener.onPedidoLoadFailed(error.message)
                    }

                })
        }
    }

    suspend fun ordersUpdateFromFirebase(
        pos: Int, orderItem: PedidoModel, iOrderUpdateListener: IOrderUpdateListener
    ) {
        return withContext(Dispatchers.IO) {
            databaseReference
                .child(orderItem.key!!)
                .setValue(orderItem)
                .addOnSuccessListener {
                    iOrderUpdateListener.onOrderUpdateSuccess(pos, "Pedido entregado")
                }
                .addOnFailureListener {
                    iOrderUpdateListener.onOrderUpdateFailed("error: ${it.message}")
                }
        }
    }

    suspend fun orderReLoadFromFirebase(pos: Int, iOrderReLoadListener: IOrderReLoadListener) {
        val pedidoModels: MutableList<PedidoModel> = ArrayList()
        return withContext(Dispatchers.IO) {
            databaseReference.orderByChild("estado").equalTo("Pendiente")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (pedidoSnapshot in snapshot.children) {
                                val pedidoModel = pedidoSnapshot.getValue(PedidoModel::class.java)
                                pedidoModel!!.key = pedidoSnapshot.key
                                pedidoModels.add(pedidoModel)
                            }
                            iOrderReLoadListener.onOrderReLoadSuccess(pos, pedidoModels)

                        } else {
                            iOrderReLoadListener.onReLoadNotOrder(pos, pedidoModels,"No hay pedidos pendientes")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        iOrderReLoadListener.onOrderReLoadFailed(error.message)
                    }

                })
        }
    }
}
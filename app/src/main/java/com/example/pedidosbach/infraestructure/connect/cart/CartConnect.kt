package com.example.pedidosbach.infraestructure.connect.cart

import com.example.pedidosbach.presentation.listener.ICartCountListener
import com.example.pedidosbach.presentation.listener.ICartAddListener
import com.example.pedidosbach.presentation.listener.ICartDeleteAllListener
import com.example.pedidosbach.presentation.listener.ICartDeleteItemListener
import com.example.pedidosbach.presentation.listener.ICartLoadListener
import com.example.pedidosbach.presentation.listener.ICartReLoadListener
import com.example.pedidosbach.presentation.listener.ICartUpdateListener
import com.example.pedidosbach.domain.model.CartModel
import com.example.pedidosbach.domain.model.product.ProductResponse
import com.example.pedidosbach.infraestructure.shared.UserApplication.Companion.prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartConnect {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("Cart")

    suspend fun countFirebase(cartCountListener: ICartCountListener) {
        val cartModels: MutableList<CartModel> = ArrayList()
        return withContext(Dispatchers.IO) {
            databaseReference
                .child(prefs.email.substringBefore('@'))
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (cartSnapshot in snapshot.children) {
                            val cartModel = cartSnapshot.getValue(CartModel::class.java)
                            cartModel!!.key = cartSnapshot.key.toString()
                            cartModels.add(cartModel)
                        }
                        cartCountListener.onCountCartSuccess(cartModels)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        cartCountListener.onCountCartFailed(error.message)
                    }
                })
        }
    }

    suspend fun addToCart(productResponse: ProductResponse, cartAddListener: ICartAddListener) {
        return withContext(Dispatchers.IO) {
            val userCart = databaseReference.child(prefs.email.substringBefore('@'))
            userCart.child(productResponse.key!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //Si el producto existe en el carro, solo lo actualiza
                        if (snapshot.exists()) {

                            val cartModel = snapshot.getValue(CartModel::class.java)
                            val updateData: MutableMap<String, Any> = HashMap()

                            cartModel!!.quantity = cartModel.quantity + 1
                            updateData["quantity"] = cartModel.quantity
                            updateData["totalPrice"] =
                                cartModel.quantity * cartModel.price!!.toFloat()

                            userCart.child(productResponse.key!!)
                                .updateChildren(updateData)
                                .addOnSuccessListener {
                                    cartAddListener.onAddCartSuccess("Agregado al carrito")
                                }
                                .addOnFailureListener { e ->
                                    cartAddListener.onAddCartFailed(e.message.toString())
                                }

                        } else { //si el producto no existe, lo agrega
                            val cartModel = CartModel()
                            cartModel.key = productResponse.key
                            cartModel.name = productResponse.name
                            cartModel.image = productResponse.image
                            cartModel.price = productResponse.price
                            cartModel.descripcion = productResponse.descripcion
                            cartModel.quantity = 1
                            cartModel.totalPrice = productResponse.price!!.toFloat()

                            userCart.child(productResponse.key!!)
                                .setValue(cartModel)
                                .addOnSuccessListener {
                                    cartAddListener.onAddCartSuccess("Agregado al carrito")
                                }
                                .addOnFailureListener { e ->
                                    cartAddListener.onAddCartFailed(e.message.toString())
                                }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        cartAddListener.onAddCartFailed(error.message)
                    }

                })
        }
    }

    suspend fun loadCart(cartLoadListener: ICartLoadListener) {
        val cartModels: MutableList<CartModel> = ArrayList()
        cartLoadListener.onLoadCartLoading()
        return withContext(Dispatchers.IO) {
            databaseReference
                .child(prefs.email.substringBefore('@'))
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (cartSnapshot in snapshot.children) {
                                val cartModel = cartSnapshot.getValue(CartModel::class.java)
                                cartModel!!.key = cartSnapshot.key
                                cartModels.add(cartModel)
                            }
                            cartLoadListener.onLoadCartSuccess(cartModels)
                        } else {
                            cartLoadListener.onNotCart()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        cartLoadListener.onLoadCartFailed(error.message)
                    }

                })
        }
    }

    suspend fun reLoadCart(cartReLoadListener: ICartReLoadListener) {
        val cartModels: MutableList<CartModel> = ArrayList()
        return withContext(Dispatchers.IO) {
            databaseReference
                .child(prefs.email.substringBefore('@'))
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (cartSnapshot in snapshot.children) {
                                val cartModel = cartSnapshot.getValue(CartModel::class.java)
                                cartModel!!.key = cartSnapshot.key
                                cartModels.add(cartModel)
                            }
                            cartReLoadListener.onReLoadCartSuccess(cartModels)
                        }else{
                            cartReLoadListener.onReLoadNotCart()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        cartReLoadListener.onReLoadCartFailed(error.message)
                    }

                })
        }
    }

    suspend fun update(cartModel: CartModel, iCartUpdateListener: ICartUpdateListener) {
        return withContext(Dispatchers.IO) {
            databaseReference
                .child(prefs.email.substringBefore('@'))
                .child(cartModel.key!!)
                .setValue(cartModel)
                .addOnSuccessListener {
                    iCartUpdateListener.onUpdateCartSuccess("actualizado")
                }
                .addOnFailureListener {
                    iCartUpdateListener.onUpdateCartFailed("error: ${it.message}")
                }
        }
    }

    suspend fun deleteItem(position: Int, cart: CartModel, iCartDeleteItemListener: ICartDeleteItemListener){
        return withContext(Dispatchers.IO){
            databaseReference
                .child(prefs.email.substringBefore('@'))
                .child(cart.key!!)
                .removeValue()
                .addOnSuccessListener {
                    iCartDeleteItemListener.onDeleteItemCartSuccess(position, cart)
                }
                .addOnFailureListener {
                    iCartDeleteItemListener.onDeleteItemCartFailed("error: ${it.message}")
                }
        }
    }

    suspend fun deleteCart(iCartDeleteAllListener: ICartDeleteAllListener){
        return withContext(Dispatchers.IO){
            databaseReference.child(prefs.email.substringBefore('@')).removeValue()
                .addOnSuccessListener {
                    iCartDeleteAllListener.onDeleteAllCartSuccess("carrito eliminado")
                }
                .addOnFailureListener {
                    iCartDeleteAllListener.onDeleteAllCartSuccess("error : ${it.message}")
                }
        }
    }
}
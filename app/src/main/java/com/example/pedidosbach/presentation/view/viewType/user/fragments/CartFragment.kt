package com.example.pedidosbach.presentation.view.viewType.user.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosbach.R
import com.example.pedidosbach.aplication.listener.ICartDeleteAllListener
import com.example.pedidosbach.aplication.listener.ICartDeleteItemListener
import com.example.pedidosbach.aplication.listener.ICartLoadListener
import com.example.pedidosbach.aplication.listener.ICartReLoadListener
import com.example.pedidosbach.aplication.listener.ICartUpdateListener
import com.example.pedidosbach.aplication.listener.IPayOrderListener
import com.example.pedidosbach.databinding.FragmentCartBinding
import com.example.pedidosbach.databinding.LayoutCartItemBinding
import com.example.pedidosbach.domain.model.CartModel
import com.example.pedidosbach.domain.model.PedidoRequest
import com.example.pedidosbach.infraestructure.shared.UserApplication.Companion.prefs
import com.example.pedidosbach.presentation.adapter.cart.CartAdapter
import com.example.pedidosbach.presentation.view.mains.FragmentFather
import com.example.pedidosbach.presentation.viewModel.cart.CartViewModel
import com.example.pedidosbach.presentation.viewModel.order.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode


class CartFragment : FragmentFather(), ICartLoadListener, ICartReLoadListener, IPayOrderListener,
    ICartUpdateListener, ICartDeleteItemListener, ICartDeleteAllListener {
    lateinit var binding: FragmentCartBinding
    lateinit var contextCart: Context
    lateinit var viewCart: View

    private val cartViewModel: CartViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    private lateinit var adapterCart: CartAdapter
    lateinit var productsCart: MutableList<CartModel>
    lateinit var opciones: Spinner
    lateinit var pago: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextCart = view.context
        viewCart = view
        loadCart()
        initRecyclerView(view.context)
        initSpinner()
        payOrderOnClickListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun payOrderOnClickListener() {
        binding.btnPagar.setOnClickListener {
            val idUser = prefs.email.substringBefore('@')
            val name = binding.Name.text.trim().toString()
            val direccion = binding.txtDirection.text.trim().toString()
            val Total = binding.txtTotal2.text.toString()
            val Metodo = pago
            val Estado = "Pendiente"
            if (name.isNotEmpty() && direccion.isNotEmpty()) {
                val order = PedidoRequest(
                    idUser, name,direccion, getDate(), getTime(), Total, Metodo, Estado, productsCart
                )
                orderViewModel.payOrder(order, this)
            } else {
                showSnackbarPay(
                    viewCart, "Llene todos los campos", R.color.gray, R.color.white
                )
            }
        }
    }

    private fun initSpinner() {
        opciones = binding.Spinner
        val lista = listOf("Efectivo")
        val adap = ArrayAdapter(contextCart, android.R.layout.simple_spinner_dropdown_item, lista)
        opciones.adapter = adap
        opciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                pago = lista[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                pago = lista[0]
            }

        }
    }


    private fun loadCart() {
        cartViewModel.load(this)
    }

    private fun initRecyclerView(context: Context) {
        adapterCart = CartAdapter(context, ArrayList(),
            { layoutCartItemBinding, cartModel -> minusCartItem(layoutCartItemBinding, cartModel) },
            { layoutCartItemBinding, cartModel -> plusCartItem(layoutCartItemBinding, cartModel) },
            { position, cartModel -> deleteCartItem(position, cartModel) })
        binding.recyclerCart.apply {
            layoutManager = LinearLayoutManager(contextCart)
            adapter = adapterCart
        }
    }

    private fun plusCartItem(bindingCart: LayoutCartItemBinding, cart: CartModel) {
        cart.quantity += 1;
        cart.totalPrice = cart.quantity * cart.price!!.toFloat()
        bindingCart.txtQuantity.text = java.lang.StringBuilder("").append(cart.quantity)
        updateFirebase(cart)
    }

    private fun minusCartItem(bindingCart: LayoutCartItemBinding, cart: CartModel) {
        if (cart.quantity > 1) {
            cart.quantity -= 1;
            cart.totalPrice = cart.quantity * cart.price!!.toFloat()
            bindingCart.txtQuantity.text = java.lang.StringBuilder("").append(cart.quantity)
            updateFirebase(cart)
        }
    }

    private fun deleteCartItem(position: Int, cart: CartModel) {
        val dialog = AlertDialog.Builder(contextCart)
            .setTitle("Eliminación de producto")
            .setMessage("¿Está seguro que desea eliminar este producto del carrito?")
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Sí") { _, _ ->
                binding.btnPagar.isEnabled = false
                cartViewModel.deleteItemCart(position, cart, this)
            }
        dialog.create().show()
    }

    private fun updateFirebase(cartModel: CartModel) {
        binding.btnPagar.isEnabled = false
        cartViewModel.update(cartModel, this)
    }

    private fun showDataCart(cartModelList: MutableList<CartModel>) {
        productsCart = cartModelList
        var sum = 0.0
        for (cartModel in cartModelList) {
            sum += cartModel.totalPrice
        }
        var bd = BigDecimal(sum)
        bd = bd.setScale(2, RoundingMode.HALF_UP)
        binding.txtTotal2.text = StringBuilder("S/. ").append(bd)
        adapterCart.refreshListCart(cartModelList)
        binding.btnPagar.isEnabled = true
    }

    private fun showCart() {
        binding.recyclerCart.visibility = View.VISIBLE
        binding.cvPay.visibility = View.VISIBLE
        binding.llLoadProducts.visibility = View.GONE
        binding.llNotProducts.visibility = View.GONE
    }

    private fun notCart() {
        binding.recyclerCart.visibility = View.GONE
        binding.cvPay.visibility = View.GONE
        binding.llLoadProducts.visibility = View.GONE
        binding.llNotProducts.visibility = View.VISIBLE
    }

    override fun onLoadCartLoading() {
        binding.recyclerCart.visibility = View.GONE
        binding.cvPay.visibility = View.GONE
        binding.llLoadProducts.visibility = View.VISIBLE
        binding.llNotProducts.visibility = View.GONE
    }

    override fun onLoadCartSuccess(cartModelList: MutableList<CartModel>) {
        showDataCart(cartModelList)
        showCart()
    }

    override fun onNotCart() {
        notCart()
    }

    override fun onLoadCartFailed(message: String) {
        Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onUpdateCartSuccess(message: String) {
        cartViewModel.reLoad(this)
    }

    override fun onUpdateCartFailed(message: String) {
        Toast.makeText(contextCart, message, Toast.LENGTH_SHORT).show()
    }

    override fun onReLoadCartSuccess(cartModelList: MutableList<CartModel>) {
        binding.btnPagar.isEnabled = true
        showDataCart(cartModelList)
    }

    override fun onReLoadNotCart() {
        notCart()
    }

    override fun onReLoadCartFailed(message: String) {
        binding.btnPagar.isEnabled = true
        Toast.makeText(contextCart, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteItemCartSuccess(position: Int, cart: CartModel) {
        adapterCart.removeItem(position)
        cartViewModel.reLoad(this)
    }

    override fun onDeleteItemCartFailed(message: String) {
        Toast.makeText(contextCart, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteAllCartSuccess(message: String) {
        cartViewModel.reLoad(this)
    }

    override fun onDeleteAllCartFailed(message: String) {
        Toast.makeText(contextCart, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPayOrderSuccess(message: String) {
        showSnackbarPay(viewCart,message, background = R.color.green, text = R.color.black)
        binding.Name.text.clear()
        binding.txtDirection.text.clear()
        cartViewModel.deleteAllCart(this)
    }

    override fun onPayOrderFailed(message: String) {
        Toast.makeText(contextCart, message, Toast.LENGTH_LONG).show()
    }
}
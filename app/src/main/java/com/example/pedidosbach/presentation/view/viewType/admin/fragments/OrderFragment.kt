package com.example.pedidosbach.presentation.view.viewType.admin.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pedidosbach.R
import com.example.pedidosbach.aplication.listener.IOrderReLoadListener
import com.example.pedidosbach.aplication.listener.IOrderUpdateListener
import com.example.pedidosbach.aplication.listener.IPedidoLoadListener
import com.example.pedidosbach.databinding.FragmentOrderBinding
import com.example.pedidosbach.domain.model.PedidoModel
import com.example.pedidosbach.presentation.adapter.order.OrderAdapter
import com.example.pedidosbach.presentation.view.mains.FragmentFather
import com.example.pedidosbach.presentation.viewModel.order.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

class OrderFragment : FragmentFather(), IPedidoLoadListener, IOrderUpdateListener,
    IOrderReLoadListener {
    lateinit var binding: FragmentOrderBinding
    lateinit var contextOrder: Context
    lateinit var viewOrder: View
    private lateinit var orderAdapter: OrderAdapter

    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextOrder = view.context
        viewOrder = view
        initRecyclerView(view.context)
        configRefresh()
        loadOrders()
    }

    private fun configRefresh() {
        configSwipeRefresh(binding.refresh, contextOrder)
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = true
            loadOrders()
        }
    }

    private fun loadOrders() {
        orderViewModel.loadOrder(this)
    }

    private fun initRecyclerView(context: Context) {
        orderAdapter =
            OrderAdapter(ArrayList()) { position, orderItem -> serveCustomer(position, orderItem) }
        binding.recyclerPedidos.apply {
            layoutManager = LinearLayoutManager(contextOrder)
            adapter = orderAdapter
        }
    }

    private fun serveCustomer(position: Int, orderItem: PedidoModel) {
        orderItem.estado = "Entregado"
        orderViewModel.updateOrder(position, orderItem, this)
    }

    private fun showOrder() {
        binding.recyclerPedidos.visibility = View.VISIBLE
        binding.llLoadOrders.visibility = View.GONE
        binding.llNotOrders.visibility = View.GONE
    }

    private fun notOrders() {
        binding.recyclerPedidos.visibility = View.GONE
        binding.llLoadOrders.visibility = View.GONE
        binding.llNotOrders.visibility = View.VISIBLE
    }

    override fun onPedidoLoadLoading() {
        binding.recyclerPedidos.visibility = View.GONE
        binding.llLoadOrders.visibility = View.VISIBLE
        binding.llNotOrders.visibility = View.GONE
    }

    override fun onPedidoLoadSuccess(pedidoModelList: MutableList<PedidoModel>) {
        binding.refresh.isRefreshing = false
        orderAdapter.refreshListProducts(pedidoModelList)
        showOrder()
    }

    override fun onPedidoLoadNot(message: String) {
        binding.refresh.isRefreshing = false
        notOrders()
    }

    override fun onPedidoLoadFailed(message: String?) {
        binding.refresh.isRefreshing = false
        try {
            Snackbar.make(binding.mainLayout, message!!, Snackbar.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.d("errorOrder", e.message.toString())
        }
    }

    override fun onOrderUpdateSuccess(position: Int, message: String) {
        showSnackbarPay(viewOrder, message, background = R.color.orange, text = R.color.white)
        orderViewModel.reLoadOrders(position, this)
    }

    override fun onOrderUpdateFailed(message: String) {
        Toast.makeText(contextOrder, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOrderReLoadSuccess(position: Int, pedidoModelList: MutableList<PedidoModel>) {
        orderAdapter.deleteOrder(position, pedidoModelList)
    }

    override fun onReLoadNotOrder(
        position: Int, pedidoModelList: MutableList<PedidoModel>, message: String
    ) {
        orderAdapter.deleteOrder(position, pedidoModelList)
        notOrders()
    }

    override fun onOrderReLoadFailed(message: String) {
        Toast.makeText(contextOrder, message, Toast.LENGTH_SHORT).show()
    }
}
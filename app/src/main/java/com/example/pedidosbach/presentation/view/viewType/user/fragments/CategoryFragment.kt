package com.example.pedidosbach.presentation.view.viewType.user.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pedidosbach.R
import com.example.pedidosbach.aplication.listener.ICartCountListener
import com.example.pedidosbach.aplication.listener.ICartAddListener
import com.example.pedidosbach.aplication.listener.IProductLoadListener
import com.example.pedidosbach.databinding.FragmentCategoryBinding
import com.example.pedidosbach.domain.model.CartModel
import com.example.pedidosbach.domain.model.product.ProductResponse
import com.example.pedidosbach.presentation.adapter.product.ProductAdapter
import com.example.pedidosbach.presentation.viewModel.cart.CartViewModel
import com.example.pedidosbach.presentation.viewModel.product.LoadProductViewModel
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


class CategoryFragment : Fragment(), IProductLoadListener, ICartAddListener, ICartCountListener {
    private lateinit var binding: FragmentCategoryBinding
    lateinit var contextCat: Context
    private lateinit var adapterProducts: ProductAdapter
    private val loadProduct: LoadProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextCat = view.context
        initRecyclerView(view.context)
        loadProductsFromFirebase()
        countCart()
    }

    private fun initRecyclerView(context: Context) {
        adapterProducts = ProductAdapter(context, ArrayList()) { onItemSelected(it) }
        binding.recyclerProduct.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterProducts
        }
    }

    private fun loadProductsFromFirebase() {
        loadProduct.onCreate(this)
    }

    private fun onItemSelected(productItem: ProductResponse) {
        addToCart(productItem)
    }

    private fun addToCart(productResponse: ProductResponse) {
        cartViewModel.add(productResponse, this)
    }

    private fun countCart() {
        cartViewModel.count(this)
    }

    override fun onProductLoading() {
        binding.llLoadProducts.visibility = View.VISIBLE
        binding.recyclerProduct.visibility = View.GONE
    }

    override fun onProductLoadSuccess(productResponseList: MutableList<ProductResponse>) {
        adapterProducts.refreshListProducts(productResponseList)
        binding.llLoadProducts.visibility = View.GONE
        binding.recyclerProduct.visibility = View.VISIBLE
    }

    override fun onNotProduct() {
        binding.llLoadProducts.visibility = View.GONE
        binding.recyclerProduct.visibility = View.GONE
        binding.llNotProducts.visibility = View.VISIBLE
    }

    override fun onProductLoadFailed(message: String?) {
        Snackbar.make(binding.mainLayout, message!!, Snackbar.LENGTH_LONG).show()
        binding.llLoadProducts.visibility = View.GONE
        binding.recyclerProduct.visibility = View.VISIBLE
    }

    override fun onAddCartSuccess(message: String) {
        countCart()
        try {
            val snackbar = Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT)
            snackbar.view.setBackgroundColor(resources.getColor(R.color.orange))
            snackbar.setActionTextColor(resources.getColor(R.color.white))
            snackbar.show()
        } catch (e: Exception) {
            Log.d("error", e.message.toString())
        }
    }

    override fun onAddCartFailed(message: String) {
        Toast.makeText(contextCat, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCountCartSuccess(cartModelList: List<CartModel>) {
        var cartSum = 0
        for (cartModel in cartModelList) cartSum += cartModel.quantity
        binding.badge.setNumber(cartSum)
    }

    override fun onCountCartFailed(message: String) {
        Toast.makeText(contextCat, message, Toast.LENGTH_SHORT).show()
    }

    private fun init() {
        /*binding.btnCart.setOnClickListener {
            val fragment = ProfileFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.dashboardfragment, fragment)?.commit()
        }*/
    }
}
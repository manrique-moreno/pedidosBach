package com.example.pedidosbach.presentation.view.viewType.admin.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.pedidosbach.R
import com.example.pedidosbach.databinding.FragmentAddProductBinding
import com.example.pedidosbach.domain.enums.EnumAddProduct
import com.example.pedidosbach.domain.enums.EnumImage
import com.example.pedidosbach.presentation.view.mains.FragmentFather
import com.example.pedidosbach.presentation.viewModel.product.AddProductViewModel
import com.example.pedidosbach.presentation.viewModel.product.UpImageViewModel


class AddProductFragment : FragmentFather() {
    lateinit var binding: FragmentAddProductBinding
    private val upImageVM: UpImageViewModel by viewModels()
    private val addProduct: AddProductViewModel by viewModels()

    var isExecutable = false
    lateinit var category: String
    private var mImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        if (container != null) {
            observersUpImage(container.context)
            observersAddProduct(container.context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configSpinner(view.context)
        initButtons(view.context)
    }

    private fun configSpinner(context: Context) {
        val spinner = binding.SpinnerCategoria
        val listSpinner = listOf("Burguer", "Pizza", "Soda")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, listSpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                category = listSpinner[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                category = listSpinner[0]
            }
        }
    }

    private fun initButtons(context: Context) {
        binding.imagenProducto.setImageResource(R.drawable.no_image)
        binding.btnElegirImagen.setOnClickListener { openFileChoose() }
        binding.btnAgregarProducto.setOnClickListener {
            uploadFile(context)
        }
    }

    private fun openFileChoose() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            mImageUri = data.data
            binding.imagenProducto.setImageURI(mImageUri)
        }
    }

    private fun uploadFile(context: Context) {
        val name = binding.txtName.text.trim().toString()
        val description = binding.txtDescripcion.text.trim().toString()
        val price = binding.txtPrice.text.trim().toString()
        if (name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && validatePrice(price)){
            if (mImageUri != null) {
                isExecutable = true
                upImageVM.onCreate(mImageUri, context)
            } else {
                Toast.makeText(context, "debe elegir una imagen", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "porfavor llene los campos correctamente", Toast.LENGTH_SHORT).show()
        }
    }

    fun validatePrice(texto: String): Boolean {
        return texto.matches(Regex("^\\d+(\\.\\d+)?$"))
    }

    private fun observersUpImage(context: Context) {
        upImageVM.response.observe(viewLifecycleOwner) { response ->
            if (response != null)
                when (response.enumImage) {
                    EnumImage.isloading -> {
                        opaqueImage(context, binding.imagenProducto)
                        hideButtonAddProduct(context)
                    }

                    EnumImage.isSuccessful -> {
                        binding.imagenProducto.colorFilter = null
                    }

                    EnumImage.isError -> {
                        showButtonAddProduct(context)
                    }

                    EnumImage.isComplete -> {
                        if (isExecutable) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                addProduct.onCreate(
                                    binding.txtName, response.urlImage, category,
                                    binding.txtPrice, binding.txtDescripcion
                                )
                            }, 1000)
                        }
                    }
                }
        }

        upImageVM.messageText.observe(viewLifecycleOwner) { message ->
            binding.txtload.text = message
        }

        upImageVM.messageToast.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observersAddProduct(context: Context) {
        addProduct.response.observe(viewLifecycleOwner) {
            when (it) {
                EnumAddProduct.isloading -> {
                    binding.txtload.text = "registrando producto..."
                    hideButtonAddProduct(context)
                }

                EnumAddProduct.isSuccessful -> {
                    if (isExecutable) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            showButtonAddProduct(context)
                            cleanTexts()
                            isExecutable = false
                        }, 1000)
                    }
                }

                EnumAddProduct.isError -> {
                    showButtonAddProduct(context)
                }

                else -> {}
            }
        }

        addProduct.message.observe(viewLifecycleOwner) { message ->
            if (isExecutable) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showButtonAddProduct(context: Context) {
        binding.progressBar.visibility = View.GONE
        binding.txtload.visibility = View.GONE
        binding.btnAgregarProducto.visibility = View.VISIBLE
        binding.btnElegirImagen.background = pathColorButton(buttonColor, context)
        binding.btnElegirImagen.setOnClickListener { openFileChoose() }
        binding.imagenProducto.setImageResource(R.drawable.no_image)
        isEnabledTexts(true)
        mImageUri = null
    }

    private fun hideButtonAddProduct(context: Context) {
        binding.btnElegirImagen.background = pathColorButton(buttonColorNull, context)
        binding.btnElegirImagen.setOnClickListener { }
        binding.btnAgregarProducto.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.txtload.visibility = View.VISIBLE
        isEnabledTexts(false)
    }

    private fun isEnabledTexts(isEnabled: Boolean) {
        binding.txtName.isEnabled = isEnabled
        binding.txtDescripcion.isEnabled = isEnabled
        binding.txtPrice.isEnabled = isEnabled
        binding.SpinnerCategoria.isEnabled = isEnabled
    }

    private fun cleanTexts() {
        binding.txtName.text.clear()
        binding.txtDescripcion.text.clear()
        binding.txtPrice.text.clear()

    }

    override fun onResume() {
        super.onResume()
        if (mImageUri != null) {
            binding.imagenProducto.setImageURI(mImageUri)
        }
    }


}
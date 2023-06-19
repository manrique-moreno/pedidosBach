package com.example.pedidosbach.presentation.view.login

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.pedidosbach.R
import com.example.pedidosbach.databinding.ActivityRegisterBinding
import com.example.pedidosbach.domain.enums.EnumRegister
import com.example.pedidosbach.presentation.view.mains.ActivityFather
import com.example.pedidosbach.presentation.viewModel.login.RegisterViewModel

class RegisterActivity : ActivityFather() {
    lateinit var binding: ActivityRegisterBinding

    val registerVM: RegisterViewModel by viewModels()
    var stateUser = false
    var statePassword = false
    var stateConfirm = false

    private val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
    private val colorRedHelper = intArrayOf(Color.RED)
    private val colorGrayHelper = intArrayOf(Color.GRAY)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsInit()
        validateInit()
        responseRegister()
    }

    private fun buttonsInit(){
        binding.btnRegister.setOnClickListener {
            Toast.makeText(this, "ingrese sus datos", Toast.LENGTH_SHORT).show()
        }

        binding.btnRegisterLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInit() {
        binding.txtEmail.addTextChangedListener(validationUser)
        binding.txtPassword.addTextChangedListener(validationPassword)
        binding.txtPasswordConfirm.addTextChangedListener(validationPasswordConfirm)
    }

    private fun validatesInput() {
        val buttonColor: String
        if (stateUser && statePassword && stateConfirm) {
            buttonColor = "background_button"
            binding.btnRegister.setOnClickListener {
                registerVM.onCreate(binding.txtEmail, binding.txtPassword)
            }
        } else {
            buttonColor = "background_button_null"
            binding.btnRegister.setOnClickListener {
                Toast.makeText(this, "Llene todos los campos correctamente", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnRegister.background = pathColorButton(buttonColor, this)

    }

    private val validationUser = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                if (s.isNotEmpty()) {
                    if (validateFormatEmail(s.toString())){
                        binding.tvEmail.setHelperTextColor(ColorStateList(states, colorGrayHelper))
                        binding.tvEmail.helperText = " "
                        stateUser = true
                    }else{
                        binding.tvEmail.setHelperTextColor(ColorStateList(states, colorRedHelper))
                        binding.tvEmail.helperText = "* Ingrese una dirección de correo válida"
                        stateUser = false
                    }
                    validatesInput()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }


    private val validationPassword = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                val chars = s.toString()
                if (chars.none { it !in 'A'..'Z' && it !in 'a'..'z' && it !in '0'..'9' }) {
                    binding.tvPassword.setHelperTextColor(ColorStateList(states, colorRedHelper))
                    binding.tvPassword.helperText = "* La contraseña debe ser mayor a 6 dígitos"

                    if (s.length >= 6) {
                        binding.tvPassword.helperText = " "
                        binding.tvPassword.setHelperTextColor(ColorStateList(states, colorGrayHelper))
                        statePassword = true
                    }else{
                        statePassword = false
                    }
                } else {
                    binding.tvPassword.helperText = "* Solo se admiten letras y números"
                    statePassword = false
                    binding.tvPassword.setHelperTextColor(ColorStateList(states, colorRedHelper))
                }
                validatesInput()
            }
            binding.txtPasswordConfirm.removeTextChangedListener(validationPasswordConfirm)
            binding.txtPasswordConfirm.addTextChangedListener(validationPasswordConfirm)
            binding.txtPasswordConfirm.text = binding.txtPasswordConfirm.text

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private val validationPasswordConfirm = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                if (s.toString() == binding.txtPassword.text.toString()) {
                    stateConfirm = true
                    binding.tvConfirm.setHelperTextColor(ColorStateList(states, colorGrayHelper))
                } else {
                    binding.tvConfirm.setHelperTextColor(ColorStateList(states, colorRedHelper))
                    binding.tvConfirm.helperText = resources.getText(R.string.password_check)
                    stateConfirm = false
                }

                validatesInput()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun responseRegister(){
        registerVM.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        registerVM.response.observe(this) {
            if (it != null) {
                when (it) {
                    EnumRegister.isloading -> {
                        binding.btnRegister.visibility = View.GONE
                        binding.progressRegister.visibility = View.VISIBLE
                    }

                    EnumRegister.isSuccessful -> {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }

                    EnumRegister.isError -> {
                        binding.btnRegister.visibility = View.VISIBLE
                        binding.progressRegister.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onBackPressed() {

    }
}
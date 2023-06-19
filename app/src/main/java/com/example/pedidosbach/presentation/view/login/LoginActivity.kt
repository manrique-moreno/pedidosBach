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
import com.example.pedidosbach.databinding.ActivityMainBinding
import com.example.pedidosbach.domain.enums.EnumLogin
import com.example.pedidosbach.domain.enums.TypeUser
import com.example.pedidosbach.infraestructure.shared.UserApplication.Companion.prefs
import com.example.pedidosbach.presentation.view.mains.ActivityFather
import com.example.pedidosbach.presentation.view.viewType.admin.MainPanelAdmin
import com.example.pedidosbach.presentation.view.viewType.user.MainPanel
import com.example.pedidosbach.presentation.viewModel.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ActivityFather() {
    lateinit var binding: ActivityMainBinding
    val loginVM: LoginViewModel by viewModels()

    var stateUser = false
    var statePassword = false

    private val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
    private val colorRedHelper = intArrayOf(Color.RED)
    private val colorGrayHelper = intArrayOf(Color.GRAY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsInit()
        validateInit()
        responselogin()

    }

    private fun buttonsInit() {
        binding.btnLogin.setOnClickListener {
            Toast.makeText(this, "Ingrese datos", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoginRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun validateInit() {
        binding.txtUser.addTextChangedListener(validationUser)
        binding.txtPassword.addTextChangedListener(validationPassword)
    }

    private fun validatesInput() {
        val buttonColor: String
        if (stateUser && statePassword) {
            buttonColor = "background_button"
            binding.btnLogin.setOnClickListener {
                loginVM.onCreate(binding.txtUser, binding.txtPassword)
            }
        } else {
            buttonColor = "background_button_null"
            binding.btnLogin.setOnClickListener {
                Toast.makeText(this, "Llene todos los campos correctamente", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.btnLogin.background = pathColorButton(buttonColor, this)

    }

    private val validationUser = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                if (s.isNotEmpty()) {
                    if (validateFormatEmail(s.toString())) {
                        binding.tvEmail.setHelperTextColor(ColorStateList(states, colorGrayHelper))
                        binding.tvEmail.helperText = " "
                        stateUser = true
                    } else {
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
                        binding.tvPassword.setHelperTextColor(
                            ColorStateList(
                                states,
                                colorGrayHelper
                            )
                        )
                        statePassword = true
                    } else {
                        statePassword = false
                    }
                } else {
                    binding.tvPassword.helperText = "* Solo se admiten letras y números"
                    statePassword = false
                    binding.tvPassword.setHelperTextColor(ColorStateList(states, colorRedHelper))
                }
                validatesInput()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun responselogin() {
        loginVM.message.observe(this) {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        loginVM.response.observe(this) {
            if (it != null) {
                when (it) {
                    EnumLogin.isloading -> {
                        binding.btnLogin.visibility = View.GONE
                        binding.progressLogin.visibility = View.VISIBLE
                    }

                    EnumLogin.isError -> {
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.progressLogin.visibility = View.GONE
                    }
                }
            }
        }

        loginVM.isTypeSuccessful.observe(this) {
            if (it == TypeUser.ADMIN) {
                startActivity(Intent(this, MainPanelAdmin::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainPanel::class.java))
                finish()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            if (prefs.email == "admin@gmail.com") {
                startActivity(Intent(this, MainPanelAdmin::class.java))
            } else {
                startActivity(Intent(this, MainPanel::class.java))
                finish()
            }
        }
    }
}
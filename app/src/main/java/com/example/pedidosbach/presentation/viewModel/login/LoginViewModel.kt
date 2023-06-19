package com.example.pedidosbach.presentation.viewModel.login

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.aplication.useCase.login.LoginUseCase
import com.example.pedidosbach.domain.enums.EnumLogin
import com.example.pedidosbach.domain.enums.ErrorFirebase
import com.example.pedidosbach.domain.enums.TypeUser
import com.example.pedidosbach.infraestructure.shared.UserApplication.Companion.prefs
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val response = MutableLiveData<EnumLogin>()
    val message = MutableLiveData<String?>()
    val isTypeSuccessful = MutableLiveData<TypeUser>()
    private val registerUC = LoginUseCase()
    private lateinit var errorCode: String

    fun onCreate(txtEmail: EditText, txtPassword: EditText) {
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()
        response.postValue(EnumLogin.isloading)

        viewModelScope.launch {
            registerUC.login(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.user?.email == "admin@gmail.com") {
                        isTypeSuccessful.postValue(TypeUser.ADMIN)
                        message.postValue("BIENVENIDO admin")
                    } else {
                        isTypeSuccessful.postValue(TypeUser.USER)
                        message.postValue("BIENVENIDO ${task.result.user?.email}")
                    }
                    prefs.email = task.result.user?.email.toString()
                } else {
                    when (val exception = task.exception) {
                        is FirebaseAuthException -> {
                            errorCode = ErrorFirebase.values()
                                .find { it.name == (exception as? FirebaseAuthException)?.errorCode }?.message.toString()
                        }

                        is FirebaseException -> {
                            errorCode = exception.message.toString()
                        }
                    }

                    message.postValue(errorCode)
                    response.postValue(EnumLogin.isError)
                }
            }
        }
    }
}
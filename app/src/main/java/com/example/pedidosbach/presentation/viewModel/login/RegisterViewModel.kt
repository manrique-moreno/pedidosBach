package com.example.pedidosbach.presentation.viewModel.login

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pedidosbach.aplication.useCase.login.RegisterUseCase
import com.example.pedidosbach.domain.enums.EnumRegister
import com.example.pedidosbach.domain.enums.ErrorFirebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val response = MutableLiveData<EnumRegister>()
    val message = MutableLiveData<String>()
    private val registerUC = RegisterUseCase()
    private lateinit var errorCode : String

    fun onCreate(txtEmail: EditText, txtPassword: EditText) {
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()
        response.postValue(EnumRegister.isloading)

        viewModelScope.launch {
           registerUC.register(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    message.postValue("Registro Exitoso")
                    response.postValue(EnumRegister.isSuccessful)
                } else {
                    when(val exception = task.exception){
                        is FirebaseAuthException ->{
                            errorCode = ErrorFirebase.values()
                                .find { it.name == (exception as? FirebaseAuthException)?.errorCode }?.message.toString()
                        }
                        is FirebaseException ->{
                            errorCode = exception.message.toString()
                        }
                    }
                    message.postValue(errorCode)
                    response.postValue(EnumRegister.isError)
                }
            }
        }
    }
}
package com.example.pedidosbach.aplication.useCase.login

import com.example.pedidosbach.infraestructure.connect.login.LoginConnect
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginUseCase {

    private val connect = LoginConnect()

    suspend fun login (email: String, password: String) : Task<AuthResult> {
        return connect.loginFirebase(email, password)
    }
}
package com.example.pedidosbach.aplication.useCase.login

import com.example.pedidosbach.infraestructure.connect.login.RegisterConnect
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class RegisterUseCase {
    private val connect = RegisterConnect()

    suspend fun register (email: String, password: String) : Task<AuthResult> {
        return connect.registerFirebase(email, password)
    }
}
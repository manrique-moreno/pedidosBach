package com.example.pedidosbach.infraestructure.connect.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginConnect {
    private val auth = FirebaseAuth.getInstance()

    suspend fun loginFirebase(email: String, password: String): Task<AuthResult> {
        return withContext(Dispatchers.IO){
            auth.signInWithEmailAndPassword(email,password)
        }
    }
}
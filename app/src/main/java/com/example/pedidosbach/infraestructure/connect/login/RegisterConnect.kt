package com.example.pedidosbach.infraestructure.connect.login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RegisterConnect {
    private val auth =  FirebaseAuth.getInstance()

    suspend fun registerFirebase(email: String, password: String): Task<AuthResult> {
        return withContext(Dispatchers.IO){
            auth.createUserWithEmailAndPassword(email,password)
        }
        /*return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    CoroutineScope(Dispatchers.Main).launch {
                        if (task.isSuccessful) {
                            enum = RegisterResponse.isSuccessful
                        } else {
                            enum = RegisterResponse.isError
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    activity as Context,
                                    "Error: " + task.exception?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        continuation.resume(enum)
                    }
                }
        }*/
    }
}
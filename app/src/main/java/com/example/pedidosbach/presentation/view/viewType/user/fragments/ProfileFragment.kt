package com.example.pedidosbach.presentation.view.viewType.user.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pedidosbach.databinding.FragmentProfileBinding
import com.example.pedidosbach.infraestructure.shared.UserApplication.Companion.prefs
import com.example.pedidosbach.presentation.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.resultEmail.text = prefs.email

        binding.btnSignOff.setOnClickListener {
            auth.signOut()

            val currentUser = auth.currentUser
            if (currentUser == null) {
                startActivity(Intent(view.context, LoginActivity::class.java))
            } else {
                Toast.makeText(view.context, "error al cerrar sesión", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
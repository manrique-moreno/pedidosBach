package com.example.pedidosbach.presentation.view.viewType.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pedidosbach.databinding.FragmentProfileAdminBinding
import com.example.pedidosbach.infraestructure.shared.UserApplication.Companion.prefs
import com.example.pedidosbach.presentation.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileAdminFragment : Fragment() {
    lateinit var binding: FragmentProfileAdminBinding
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileAdminBinding.inflate(inflater, container, false)
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
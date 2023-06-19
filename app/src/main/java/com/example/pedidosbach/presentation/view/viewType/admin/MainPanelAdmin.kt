package com.example.pedidosbach.presentation.view.viewType.admin

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pedidosbach.R
import com.example.pedidosbach.databinding.ActivityMainPanelAdminBinding
import com.example.pedidosbach.presentation.view.mains.ActivityFather
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPanelAdmin : ActivityFather() {
    lateinit var binding: ActivityMainPanelAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPanelAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.dashboardfragmentAdmin)
        binding.bottomnavigationAdmin.setupWithNavController(navController)
    }

    override fun onBackPressed() {}
}
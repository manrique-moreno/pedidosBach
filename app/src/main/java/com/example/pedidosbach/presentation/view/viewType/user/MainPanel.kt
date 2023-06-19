package com.example.pedidosbach.presentation.view.viewType.user

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pedidosbach.R
import com.example.pedidosbach.databinding.ActivityMainPanelBinding
import com.example.pedidosbach.presentation.view.mains.ActivityFather
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPanel : ActivityFather() {
    lateinit var binding: ActivityMainPanelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomnavigation)
        val navController = findNavController(R.id.dashboardfragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.categoryFragment, R.id.cartFragment, R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


        bottomNavigation.setupWithNavController(navController)

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }
}
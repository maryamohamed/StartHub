package com.training.starthub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.training.starthub.R
import com.training.starthub.databinding.ActivityCustomerHomeBinding

class CustomerHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavigationBar()
    }
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNavigationBar
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {

                R.id.navigation_home,
                R.id.navigation_favorites,
                R.id.navigation_cart,
                R.id.navigation_profile -> showBottomNavigationView(bottomNavigationView)
            }
        }
    }
    private fun showBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.visibility = View.VISIBLE
    }

}
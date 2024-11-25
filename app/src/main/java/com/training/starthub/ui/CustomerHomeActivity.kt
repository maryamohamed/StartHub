package com.training.starthub.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.training.starthub.R
import com.training.starthub.databinding.ActivityCustomerHomeBinding

class CustomerHomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityCustomerHomeBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate binding after setting content view
        binding = ActivityCustomerHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set up toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        // Initialize DrawerLayout and NavController
        drawerLayout = binding.drawer
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        // Set up the toggle for the drawer layout
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // Set up navigation view listener
        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)
        // Set up BottomNavigationView with NavController
        setupBottomNavigationBar()
        // Set up AppBarConfiguration with top-level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_favorites,
                R.id.navigation_cart,
                R.id.navigation_profile
            ), drawerLayout
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navigationView, navController)

        // Load default destination from navigation graph
        if (savedInstanceState == null) {
            navController.navigate(R.id.navigation_home)
        }
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = binding.bottomNavigationBar
        bottomNavigationView.setupWithNavController(navController)
        // Handle visibility for BottomNavigationView based on destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home,
                R.id.navigation_favorites,
                R.id.navigation_cart,
                R.id.navigation_profile,
                R.id.navigation_search,
                R.id.nav_Companies,   // Drawer fragment navigation IDs
                R.id.nav_products -> showBottomNavigationView(bottomNavigationView)
                else -> hideBottomNavigationView(bottomNavigationView)
            }
        }
    }

    private fun showBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.visibility = View.GONE
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_Companies -> navController.navigate(R.id.nav_Companies)
            R.id.nav_products -> navController.navigate(R.id.nav_products)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

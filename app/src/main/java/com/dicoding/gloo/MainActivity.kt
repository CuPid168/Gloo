package com.dicoding.gloo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dicoding.gloo.databinding.ActivityMainBinding
import com.dicoding.gloo.ui.gloocam.GloocamActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.mainBottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        if (::navController.isInitialized) {
            navView.post {
                navView.setupWithNavController(navController)
            }
        } else {
            Log.e("MainActivity", "NavController is not initialized")
        }

        binding.fabGloocam.setOnClickListener {
            val intent = Intent(this, GloocamActivity::class.java)
            startActivity(intent)
        }
    }
}
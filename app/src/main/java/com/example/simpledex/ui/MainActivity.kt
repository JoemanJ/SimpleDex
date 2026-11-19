package com.example.simpledex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.simpledex.R
import com.example.simpledex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        binding.imageList.setOnClickListener(this)
//        binding.imageSearch.setOnClickListener(this)
//        binding.imageFavorites.setOnClickListener(this)
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFrag.navController
        binding.bottomNavMenu.setupWithNavController(navController)
    }

    override fun onClick(v: View) {
        val navController = findNavController(R.id.fragmentContainerView)
        when(v.id){
//            binding.imageSearch.id -> {
//                navController.navigate(R.id.searchScreenFragment)
//            }
//            binding.imageList.id -> {
//                navController.navigate(R.id.listScreenFragment)
//            }
//            binding.imageFavorites.id -> {
//                navController.navigate(R.id.favoritesScreenFragment)
//            }
        }
    }
}
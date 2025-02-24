package com.tdavidc.dev.views.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.cardview.widget.CardView
import com.google.android.material.navigation.NavigationBarView
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityMainBinding
import com.tdavidc.dev.views.base.BaseActivity
import com.tdavidc.dev.views.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import android.util.Pair
import android.widget.ImageView

//TODO: move logic to VM
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflateView(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
        setupListeners()
    }

    private fun setupListeners() {
        val imgContainer = findViewById<CardView>(R.id.profile_picture_container)
        val imgView = findViewById<ImageView>(R.id.profile_picture)

        binding.topNavigationBar.setOnClickProfilePicture {
            Intent(this, ProfileActivity::class.java).also {
                startActivity(
                    it,
                    ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        Pair.create(imgContainer, "profile_picture_container"),
                        Pair.create(imgView, "profile_picture")
                    ).toBundle()
                )
            }
        }
    }

    private fun setupNavigation() {
        binding.mainBottomNavigationBar.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.home -> {
                        //TODO: handle fragment instances better
                        val currentFragment = HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, currentFragment).commit()
                        return true
                    }

                    R.id.invest -> {
                        val currentFragment = InvestFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, currentFragment).commit()
                        return true
                    }

                    R.id.payment -> {
                        val currentFragment = PaymentFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, currentFragment).commit()
                        return true
                    }

                    R.id.crypto -> {
                        val currentFragment = CryptoFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, currentFragment).commit()
                        return true
                    }

                    R.id.rovPoints -> {
                        val currentFragment = RovPointsFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, currentFragment).commit()
                        return true
                    }
                }

                return false
            }
        })
    }
}
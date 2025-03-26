package com.tdavidc.dev.ui.view.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.MenuItem
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityMainBinding
import com.tdavidc.dev.ui.view.base.BaseActivity
import com.tdavidc.dev.ui.view.main.views.TopNavigationBarView
import com.tdavidc.dev.ui.view.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

//TODO: move logic to VM
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflateView(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        handleInsets = false

        super.onCreate(savedInstanceState)

        handleSystemInsets()
        setupNavigation()
        setupListeners()
    }

    private fun handleSystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            ).also {
                val verticalPadding =
                    resources.getDimension(R.dimen.screen_vertical_padding).toInt()
                val topNavBar = v.findViewById<TopNavigationBarView>(R.id.top_navigation_bar)
                val bottomNavBar =
                    v.findViewById<BottomNavigationView>(R.id.main_bottom_navigation_bar)
                topNavBar.updatePadding(top = it.top + verticalPadding)
                bottomNavBar.updatePadding(bottom = it.bottom + verticalPadding)
            }
            WindowInsetsCompat.CONSUMED
        }
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
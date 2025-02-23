package com.tdavidc.dev.views.main

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationBarView
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityMainBinding
import com.tdavidc.dev.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

//TODO: move logic to VM
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflateView(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
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
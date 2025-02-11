package com.tdavidc.dev.views

import android.os.Bundle
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityMainBinding
import com.tdavidc.dev.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = getColor(R.color.black2)
    }
}
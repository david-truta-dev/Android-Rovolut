package com.tdavidc.dev.views

import android.os.Bundle
import com.tdavidc.dev.databinding.ActivityHomeBinding
import com.tdavidc.dev.views.base.BaseActivity

class HomeFragment : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
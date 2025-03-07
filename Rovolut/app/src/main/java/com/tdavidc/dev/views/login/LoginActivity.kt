package com.tdavidc.dev.views.login

import android.os.Bundle
import com.tdavidc.dev.databinding.ActivityLoginBinding
import com.tdavidc.dev.views.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun inflateView(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}
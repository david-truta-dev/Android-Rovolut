package com.tdavidc.dev.views.login

import android.os.Bundle
import androidx.activity.viewModels
import com.tdavidc.dev.databinding.ActivityLoginBinding
import com.tdavidc.dev.utilities.Navigator
import com.tdavidc.dev.viewmodels.base.UIModel
import com.tdavidc.dev.viewmodels.login.LoginViewModel
import com.tdavidc.dev.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels()

    override fun inflateView(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupListeners()
    }

    override fun bindViewModel() {
        viewModel.loginResponse.observe(this) {
            when (it) {
                is UIModel.Loading -> {
                    showLoading()
                }
                is UIModel.Error -> {
                    hideLoading()
                }
                is UIModel.Success -> {
                    hideLoading()
                    Navigator.goToAuthorize(this)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.continueBtn.setOnClickListener {
            viewModel.login("", "")
        }
    }
}
package com.tdavidc.dev.views

import android.content.Intent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityAuthorizeBinding
import com.tdavidc.dev.views.base.BaseActivity
import com.tdavidc.dev.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class AuthorizeActivity : BaseActivity<ActivityAuthorizeBinding>() {
//    private val viewModel: AuthorizeViewModel by viewModels()

    override fun inflateView(): ActivityAuthorizeBinding =
        ActivityAuthorizeBinding.inflate(layoutInflater)

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onStart() {
        super.onStart()
        promptToAuthorize()
    }

    private fun promptToAuthorize() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, biometricResponseHandler)

        promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_authorization_text))
                .setNegativeButtonText(getString(R.string.cancel_biometric_authorization))
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private val biometricResponseHandler = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)

            Intent(this@AuthorizeActivity, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }.also {
                startActivity(it)
                overridePendingTransition(R.anim.slide_from_bottom, R.anim.fade_out)
            }
        }
    }
}
package com.tdavidc.dev.views.authorize

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityAuthorizeBinding
import com.tdavidc.dev.viewmodels.authorize.AuthorizationStatus
import com.tdavidc.dev.viewmodels.authorize.AuthorizeViewModel
import com.tdavidc.dev.viewmodels.authorize.PasscodeLastStatus
import com.tdavidc.dev.viewmodels.base.UIModel
import com.tdavidc.dev.views.base.BaseActivity
import com.tdavidc.dev.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizeActivity : BaseActivity<ActivityAuthorizeBinding>() {
    private val viewModel: AuthorizeViewModel by viewModels()

    override fun inflateView(): ActivityAuthorizeBinding =
        ActivityAuthorizeBinding.inflate(layoutInflater)

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val biometricResponseHandler = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            viewModel.biometricAuthSuccess()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            viewModel.biometricAuthCancelled()
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        viewModel.passcodeLastAction.observe(this) {
            when (it) {
                is PasscodeLastStatus.ADDED -> {
                    binding.passcodeView.highlightDot(it.index)
                    hideFingerprintBtn()
                }

                is PasscodeLastStatus.REMOVED -> {
                    binding.passcodeView.unHighlightDot(it.index)
                    if (it.passcode.isEmpty()) showFingerprintBtn()
                }
            }
        }
        viewModel.isAuthorized.observe(this) {
            when (it) {
                is UIModel.Success -> {
                    hideLoading()
                    when (it.data) {
                        AuthorizationStatus.AUTHORIZED -> {
                            Intent(this@AuthorizeActivity, MainActivity::class.java).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }.also { intent ->
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_from_bottom, R.anim.fade_out)
                            }
                        }

                        AuthorizationStatus.REJECTED -> {
                            //TODO: add shake animation
                            binding.passcodeView.unHighlightAllDots()
                            showFingerprintBtn()
                        }

                        AuthorizationStatus.SHOW_BIOMETRIC -> {
                            showHidePasscodeViews(false)
                            biometricPrompt.authenticate(promptInfo)
                        }

                        AuthorizationStatus.BIOMETRIC_CANCELLED -> showHidePasscodeViews(true)
                    }
                }

                is UIModel.Error -> hideLoading()
                is UIModel.Loading -> showLoading()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showFingerprintBtn()
        setupBiometricPrompt()
        setupNumericKeyboard()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startBiometricAuth()
    }

    private fun setupBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, biometricResponseHandler)
        promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_authorization_text))
                .setNegativeButtonText(getString(R.string.cancel_biometric_authorization))
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .build()
    }

    private fun setupNumericKeyboard() {
        binding.apply {
            numericKeyboardView.setOnDigitClickListener { viewModel.digitClicked(it) }
            numericKeyboardView.setOnBackspaceClickListener { viewModel.backSpaceClicked() }
        }
    }

    private fun showFingerprintBtn() {
        binding.numericKeyboardView.setBottomRightButton(R.drawable.ic_fingerprint) {
            viewModel.startBiometricAuth()
        }
    }

    private fun hideFingerprintBtn() {
        binding.numericKeyboardView.resetBottomRightButton()
    }

    private fun showHidePasscodeViews(show: Boolean) {
        binding.authByCodeContainer.animate().apply {
            if (show) {
                duration = 500
                alpha(1f)
            } else {
                duration = 300
                alpha(0f)
            }
        }.start()
    }
}
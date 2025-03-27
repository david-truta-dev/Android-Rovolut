package com.tdavidc.dev.ui.screens.authorize

import android.os.Bundle
import androidx.activity.viewModels
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityAuthorizeBinding
import com.tdavidc.dev.ui.common.base.BaseActivity
import com.tdavidc.dev.ui.common.base.UIModel
import com.tdavidc.dev.utility.extensions.getDuration
import com.tdavidc.dev.utility.navigation.Navigator
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
                    when (it.data) {
                        AuthorizationStatus.AUTHORIZED -> {
                            Navigator.goToMainActivity(this@AuthorizeActivity)
                        }

                        AuthorizationStatus.REJECTED -> {
                            binding.passcodeView.unHighlightAllDots()
                            showFingerprintBtn()
                        }

                        AuthorizationStatus.SHOW_BIOMETRIC -> {
                            showHidePasscodeViews(false)
                            biometricPrompt.authenticate(promptInfo)
                        }

                        AuthorizationStatus.BIOMETRIC_CANCELLED -> showHidePasscodeViews(true)
                    }
                    binding.numericKeyboardView.enable()
                }

                is UIModel.Error -> binding.numericKeyboardView.enable()

                is UIModel.Loading -> binding.numericKeyboardView.disable()
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
                duration = resources.getDuration(R.integer.anim_duration_long)
                alpha(1f)
            } else {
                duration = resources.getDuration(R.integer.anim_duration_normal)
                alpha(0f)
            }
        }.start()
    }
}
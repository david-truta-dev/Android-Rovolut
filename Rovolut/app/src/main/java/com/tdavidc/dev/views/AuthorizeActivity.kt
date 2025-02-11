package com.tdavidc.dev.views

import android.content.Intent
import android.os.Bundle
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.tdavidc.dev.databinding.ActivityAuthorizeBinding
import com.tdavidc.dev.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

class AuthorizeActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthorizeBinding

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    Intent(this@AuthorizeActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }.also {
                        startActivity(it)
                        finish()
                    }
                }
            })
        // Lets the user authenticate using either a Class 3 biometric or
// their lock screen credential (PIN, pattern, or password).
        promptInfo =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        biometricPrompt.authenticate(promptInfo)
    }

}
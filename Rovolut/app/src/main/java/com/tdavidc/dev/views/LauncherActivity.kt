package com.tdavidc.dev.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import com.tdavidc.dev.databinding.ActivityLauncherBinding
import com.tdavidc.dev.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

// MainActivity is used only as the initial cold start of the application!
// Do not navigate back to it!
class LauncherActivity : BaseActivity() {

    private lateinit var binding: ActivityLauncherBinding

    private var continueAfterSplashAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            delayFirstDrawForSplashAnimation()
        }

        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: add app update mechanism logic
    }

    private fun delayFirstDrawForSplashAnimation() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (continueAfterSplashAnimation) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            })
        Handler(Looper.getMainLooper()).postDelayed({
            continueAfterSplashAnimation = true
            goToAuthorize()
        }, 1000)
    }

    override fun onStart() {
        super.onStart()

        //TODO: add authentication check
        goToAuthorize()
    }

    private fun goToAuthorize() {
        if (!continueAfterSplashAnimation) return

        Intent(this, AuthorizeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            startActivity(it)
        }
    }

}
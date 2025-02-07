package com.tdavidc.dev.views

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.tdavidc.dev.databinding.ActivityMainBinding

// MainActivity is used only as the initial cold start of the application!
// Do not navigate back to it!
class MainActivity : AppCompatActivity() {
    private var continueAfterSplashAnimation = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            delayFirstDrawForSplashAnimation()
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: add logged in / logged out logic
        //TODO: add app update mechanism logic
        //TODO: add viewModels, BaseActivity, and so on..
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
        }, 1000)
    }
}
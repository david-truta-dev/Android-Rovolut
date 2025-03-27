package com.tdavidc.dev.ui.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import com.tdavidc.dev.databinding.ActivityLauncherBinding
import com.tdavidc.dev.ui.view.base.BaseActivity
import com.tdavidc.dev.ui.viewmodel.LauncherViewModel
import com.tdavidc.dev.utility.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint

// LauncherActivity is used only as the initial cold start of the application!
// Therefore, please DO NOT navigate back to it!
@AndroidEntryPoint
class LauncherActivity : BaseActivity<ActivityLauncherBinding>() {
    private val viewModel: LauncherViewModel by viewModels()

    override fun inflateView(): ActivityLauncherBinding =
        ActivityLauncherBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            delayFirstDrawForSplashAnimation()
        }

        viewModel.viewIsCreated()
    }

    override fun bindViewModel() {
        viewModel.combinedLiveData.observe(this@LauncherActivity) {
            if (it) {
                Navigator.goToAuthorize(this@LauncherActivity)
            } else {
                Navigator.goToWelcomeActivity(this@LauncherActivity)
            }
        }
    }

    private fun delayFirstDrawForSplashAnimation() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.continueAfterSplashAnimation.value == true) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true // Start drawing.
                    } else {
                        false // Suspend.
                    }
                }
            })
    }
}
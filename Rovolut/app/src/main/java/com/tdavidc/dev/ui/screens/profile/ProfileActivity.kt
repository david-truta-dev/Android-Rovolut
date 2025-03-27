package com.tdavidc.dev.ui.screens.profile

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.activity.viewModels
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityProfileBinding
import com.tdavidc.dev.ui.common.base.BaseActivity
import com.tdavidc.dev.utility.extensions.getDuration
import com.tdavidc.dev.utility.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun inflateView() = ActivityProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTransitions()
        setupListeners()
    }

    override fun bindViewModel() {
        viewModel.logoutResponse.observe(this) {
            Navigator.goToWelcomeActivity(this@ProfileActivity)
        }
    }

    private fun setupTransitions() {
        window.enterTransition = Slide(Gravity.START).apply {
            duration = resources.getDuration(R.integer.anim_duration_fast)
        }
        window.exitTransition = Slide(Gravity.END).apply {
            duration = resources.getDuration(R.integer.anim_duration_fast)
        }
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener {
            finishAfterTransition()
        }
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}
package com.tdavidc.dev.views.profile

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.activity.viewModels
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityProfileBinding
import com.tdavidc.dev.utilities.extensions.getDuration
import com.tdavidc.dev.viewmodels.profile.ProfileViewModel
import com.tdavidc.dev.views.base.BaseActivity

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun inflateView() = ActivityProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTransitions()
        setupListeners()
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
    }
}
package com.tdavidc.dev.ui.screens.profile

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.activity.viewModels
import com.tdavidc.dev.R
import com.tdavidc.dev.data.source.model.User
import com.tdavidc.dev.databinding.ActivityProfileBinding
import com.tdavidc.dev.ui.common.base.BaseActivity
import com.tdavidc.dev.ui.common.base.UIModel
import com.tdavidc.dev.utility.extensions.getDuration
import com.tdavidc.dev.utility.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

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
        viewModel.user.observe(this) {
            when (it) {
                is UIModel.Loading -> showLoading()
                is UIModel.Error -> {
                    hideLoading()
//                    showErrorMessage(it.message)
                }

                is UIModel.Success -> {
                    hideLoading()
                    updateUserViews(it.data)
                }
            }
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

    @SuppressLint("SetTextI18n")
    private fun updateUserViews(user: User) {
        binding.apply {
            nameTextView.text = "${user.firstName} ${user.lastName}"
            userTagTextView.text = "@${user.userTag}"
        }
    }
}
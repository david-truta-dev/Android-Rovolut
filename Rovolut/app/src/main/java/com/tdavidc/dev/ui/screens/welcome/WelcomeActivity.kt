package com.tdavidc.dev.ui.screens.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieDrawable
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityWelcomeBinding
import com.tdavidc.dev.utility.navigation.Navigator
import com.tdavidc.dev.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun inflateView(): ActivityWelcomeBinding =
        ActivityWelcomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWelcomeScreens()
        setListeners()
    }

    override fun bindViewModel() {
        viewModel.welcomeScreens.observe(this) { screens ->
           binding.storyBarView.setup(screens.size, WelcomeViewModel.SCREEN_CHANGE_INTERVAL)
        }
        viewModel.currentScreenIndex.observe(this) { index ->
            viewModel.welcomeScreens.value?.let { screens ->
                viewModel.stopAnimation()
                updateUI(screens, index)
                viewModel.startTimer()
                viewModel.startAnimation()
            }
        }
        viewModel.lastInputCommand.observe(this) { state ->
            when (state) {
                LastInputCommand.PLAY -> {
                    binding.backgroundView.playAnimation()
                }

                LastInputCommand.PAUSE -> {
                    binding.backgroundView.pauseAnimation()
                    viewModel.currentScreenIndex.value?.let {
                        binding.storyBarView.pause(it)
                    }
                }

                LastInputCommand.RESUME -> {
                    if (binding.backgroundView.frame != binding.backgroundView.maxFrame.toInt()) {
                        // only resume if the animation is not at the end
                        binding.backgroundView.resumeAnimation()
                    }
                    viewModel.currentScreenIndex.value?.let {
                        binding.storyBarView.resume(it)
                    }
                }

                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeAnimation()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopAnimation()
    }

    private fun setWelcomeScreens() {
        viewModel.setWelcomeScreens(
            arrayListOf(
                WelcomeScreen(
                    title = R.string.welcome_title_1,
                    background = R.raw.anim_welcome_rocket,
                    dark = true
                ),
                WelcomeScreen(
                    title = R.string.welcome_title_2,
                    description = R.string.welcome_description_2,
                    background = R.raw.anim_welcome_card,
                    dark = false
                ),
                WelcomeScreen(
                    title = R.string.welcome_title_3,
                    description = R.string.welcome_description_3,
                    background = R.raw.anim_welcome_world,
                    dark = true
                ),
                WelcomeScreen(
                    title = R.string.welcome_title_4,
                    description = R.string.welcome_description_4,
                    background = R.raw.anim_welcome_coin,
                    dark = true
                ),
                WelcomeScreen(
                    title = R.string.welcome_title_5,
                    background = R.raw.anim_welcome_secure,
                    dark = false
                ),
                WelcomeScreen(
                    title = R.string.welcome_title_6,
                    background = R.raw.anim_welcome_support,
                    dark = true,
                    repeatAnimation = true
                )
            )
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {
        var isLongPress = false
        val checkLongPressHandler = Handler(Looper.getMainLooper())

        binding.backgroundView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isLongPress = false
                    checkLongPressHandler.postDelayed({
                        isLongPress = true
                        viewModel.pauseAnimation()
                    }, LONG_PRESS_PAUSE_THRESHOLD)
                    true
                }

                MotionEvent.ACTION_UP -> {
                    checkLongPressHandler.removeCallbacksAndMessages(null)
                    if (isLongPress) {
                        viewModel.resumeAnimation()
                    } else {
                        val isLeftHalf = event.x < binding.root.width / 2
                        if (isLeftHalf) viewModel.goToPreviousScreen()
                        else viewModel.goToNextScreen()
                    }
                    true
                }

                else -> return@setOnTouchListener false
            }
        }

        binding.loginButton.setOnClickListener {
            Navigator.goToLogin(this)
        }

        binding.createAccountButton.setOnClickListener {
            Navigator.goToCreateAccount(this)
        }
    }

    private fun updateUI(screens: List<WelcomeScreen>, index: Int) {
        screens.getOrNull(index)?.let { currentScreen ->
            updateCurrentScreenUI(currentScreen)
        }
        updateScreensDataUI(screens, index)
    }

    private fun updateCurrentScreenUI(screen: WelcomeScreen) {
        binding.apply {
            backgroundView.setAnimation(screen.background)
            titleTextView.text = getString(screen.title)
            if (screen.description != null) {
                descriptionTextView.isVisible = true
                descriptionTextView.text = getString(screen.description)
            } else {
                descriptionTextView.isVisible = false
            }
            WindowCompat.getInsetsController(this@WelcomeActivity.window, binding.root).apply {
                isAppearanceLightStatusBars = !screen.dark
            }
            if (screen.dark) {
                mainContainer.setBackgroundColor(getColor(R.color.black))
                headerTextView.setTextColor(getColor(R.color.white))
                headerIcon.setColorFilter(getColor(R.color.white))
                titleTextView.setTextColor(getColor(R.color.white))
                descriptionTextView.setTextColor(getColor(R.color.white))
            } else {
                mainContainer.setBackgroundColor(getColor(R.color.white))
                headerTextView.setTextColor(getColor(R.color.black))
                headerIcon.setColorFilter(getColor(R.color.black))
                titleTextView.setTextColor(getColor(R.color.black))
                descriptionTextView.setTextColor(getColor(R.color.black))
            }
            if (screen.repeatAnimation) {
                backgroundView.repeatCount = LottieDrawable.INFINITE
            } else {
                backgroundView.repeatCount = 0
            }
        }
    }

    private fun updateScreensDataUI(screens: List<WelcomeScreen>, index: Int) {
        screens.getOrNull(index)?.dark?.let {
            binding.storyBarView.setCurrentBar(index, it)
        }
    }

    companion object {
        private const val LONG_PRESS_PAUSE_THRESHOLD = 150L
    }
}

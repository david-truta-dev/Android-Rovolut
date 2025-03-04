package com.tdavidc.dev.views.welcome

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ActivityWelcomeBinding
import com.tdavidc.dev.viewmodels.welcome.WelcomeScreen
import com.tdavidc.dev.viewmodels.welcome.WelcomeViewModel
import com.tdavidc.dev.views.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    private val viewModel: WelcomeViewModel by viewModels()

    //TODO: move timer logic to the viewModel
    private var countDownTimer: CountDownTimer? = null
    private var remainingTime = SCREEN_CHANGE_INTERVAL // Time remaining for the current cycle
    private var isTimerRunning = false

    override fun inflateView(): ActivityWelcomeBinding =
        ActivityWelcomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWelcomeScreens()
        setListeners()
    }

    override fun bindViewModel() {
        viewModel.welcomeScreens.observe(this) { screens ->
            viewModel.currentScreenIndex.value?.let { index ->
                updateUI(screens, index)
                resumeTimerAndAnimation()
            }
        }
        viewModel.currentScreenIndex.observe(this) { index ->
            viewModel.welcomeScreens.value?.let { screens ->
                updateUI(screens, index)
                startTimerAndAnimation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resumeTimerAndAnimation()
    }

    override fun onPause() {
        super.onPause()
        pauseTimerAndAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimerAndAnimation()
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
                        pauseTimerAndAnimation()
                    }, LONG_PRESS_PAUSE_THRESHOLD)
                    true
                }

                MotionEvent.ACTION_UP -> {
                    checkLongPressHandler.removeCallbacksAndMessages(null)
                    if (isLongPress) {
                        resumeTimerAndAnimation()
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
                TextViewCompat.setCompoundDrawableTintList(
                    headerTextView,
                    ColorStateList.valueOf(getColor(R.color.white))
                )
                titleTextView.setTextColor(getColor(R.color.white))
                descriptionTextView.setTextColor(getColor(R.color.white))
            } else {
                mainContainer.setBackgroundColor(getColor(R.color.white))
                headerTextView.setTextColor(getColor(R.color.black))
                TextViewCompat.setCompoundDrawableTintList(
                    headerTextView,
                    ColorStateList.valueOf(getColor(R.color.black))
                )
                titleTextView.setTextColor(getColor(R.color.black))
                descriptionTextView.setTextColor(getColor(R.color.black))
            }
        }
    }

    private fun updateScreensDataUI(screens: List<WelcomeScreen>, index: Int) {
        // You can add any other UI updates if needed
    }

    private fun startTimer() {
        if (isTimerRunning) return
        countDownTimer = object : CountDownTimer(remainingTime, SCREEN_CHANGE_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                stopTimerAndAnimation()
                viewModel.goToNextScreen()
            }
        }.start()
        isTimerRunning = true
    }

    private fun startTimerAndAnimation() {
        startTimer()
        binding.backgroundView.playAnimation()
    }

    private fun pauseTimerAndAnimation() {
        binding.backgroundView.pauseAnimation()
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    private fun resumeTimerAndAnimation() {
        if (!isTimerRunning) {
            binding.backgroundView.resumeAnimation()
            startTimer()
        }
    }

    private fun stopTimerAndAnimation() {
        binding.backgroundView.pauseAnimation()
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false
        remainingTime = SCREEN_CHANGE_INTERVAL // Reset remaining time
    }

    companion object {
        private const val LONG_PRESS_PAUSE_THRESHOLD = 250L
        private const val SCREEN_CHANGE_INTERVAL = 4500L
        private const val SCREEN_CHANGE_TIMER_INTERVAL = 100L
    }
}

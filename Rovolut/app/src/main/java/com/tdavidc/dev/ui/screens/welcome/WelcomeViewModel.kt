package com.tdavidc.dev.ui.screens.welcome

import android.os.CountDownTimer
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tdavidc.dev.ui.base.BaseViewModel
import kotlinx.parcelize.Parcelize


class WelcomeViewModel : BaseViewModel() {
    private var countDownTimer: CountDownTimer? = null
    private var remainingTime = SCREEN_CHANGE_INTERVAL // Time remaining for the current cycle
    private var isTimerRunning = false

    private val _welcomeScreens by lazy { MutableLiveData<ArrayList<WelcomeScreen>>() }

    private val _currentScreenIndex by lazy { MutableLiveData(0) }

    private val _currentScreenProgress by lazy { MutableLiveData<Float>() }

    // OUTPUT: ------------
    val currentScreenIndex: LiveData<Int> = _currentScreenIndex

    val welcomeScreens: LiveData<ArrayList<WelcomeScreen>> = _welcomeScreens

    val currentScreenProgress: LiveData<Float> = _currentScreenProgress

    // INPUT: --------------

    fun setWelcomeScreens(screens: ArrayList<WelcomeScreen>) {
        _welcomeScreens.value = screens
        resetAnimation()
        startTimer()
    }

    fun goToNextScreen() {
        if (_currentScreenIndex.value == _welcomeScreens.value?.size?.minus(1)) {
            _currentScreenIndex.value = 0
        } else {
            _currentScreenIndex.value = _currentScreenIndex.value?.plus(1)
        }
        resetAnimation()
        startTimer()
    }

    fun goToPreviousScreen() {
        if (_currentScreenIndex.value == 0) {
            _currentScreenIndex.value = 0
        } else {
            _currentScreenIndex.value = _currentScreenIndex.value?.minus(1)
        }
        resetAnimation()
        startTimer()
    }

    fun pauseAnimation() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    fun resumeAnimation() {
        if (!isTimerRunning) {
            startTimer()
        }
    }

    private fun startTimer() {
        if (isTimerRunning) return

        countDownTimer = object : CountDownTimer(remainingTime, SCREEN_CHANGE_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
                _currentScreenProgress.value =
                    1.0f - millisUntilFinished.toFloat() / SCREEN_CHANGE_INTERVAL
            }

            override fun onFinish() {
                goToNextScreen()
            }
        }.start()

        isTimerRunning = true
    }

    private fun resetAnimation() {
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false
        remainingTime = SCREEN_CHANGE_INTERVAL
    }

    companion object {
        const val SCREEN_CHANGE_INTERVAL = 4500L
        private const val SCREEN_CHANGE_TIMER_INTERVAL = SCREEN_CHANGE_INTERVAL / 1000
    }
}

@Parcelize
data class WelcomeScreen(
    val title: Int,
    val description: Int? = null,
    val background: Int,
    val dark: Boolean,
    val repeatAnimation: Boolean = false
) : Parcelable

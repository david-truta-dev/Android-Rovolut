package com.tdavidc.dev.ui.viewmodel.welcome

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tdavidc.dev.ui.viewmodel.base.BaseViewModel

class WelcomeViewModel : BaseViewModel() {
    private var countDownTimer: CountDownTimer? = null
    private var remainingTime = SCREEN_CHANGE_INTERVAL // Time remaining for the current cycle
    private var isTimerRunning = false

    private val _welcomeScreens by lazy { MutableLiveData<ArrayList<WelcomeScreen>>() }

    private val _currentScreenIndex by lazy { MutableLiveData(0) }

    private val _lastInputCommand by lazy { MutableLiveData<LastInputCommand>() }

    // OUTPUT: ------------
    val currentScreenIndex: LiveData<Int> = _currentScreenIndex

    val lastInputCommand: LiveData<LastInputCommand> = _lastInputCommand

    val welcomeScreens: LiveData<ArrayList<WelcomeScreen>> = _welcomeScreens

    // INPUT: --------------

    fun setWelcomeScreens(screens: ArrayList<WelcomeScreen>) {
        _welcomeScreens.value = screens
    }

    fun goToNextScreen() {
        if (_currentScreenIndex.value == _welcomeScreens.value?.size?.minus(1)) {
            _currentScreenIndex.value = 0
        } else {
            _currentScreenIndex.value = _currentScreenIndex.value?.plus(1)
        }
    }

    fun goToPreviousScreen() {
        if (_currentScreenIndex.value == 0) {
            _currentScreenIndex.value = 0
        } else {
            _currentScreenIndex.value = _currentScreenIndex.value?.minus(1)
        }
    }

    fun startTimer() {
        if (isTimerRunning) return

        countDownTimer = object : CountDownTimer(remainingTime, SCREEN_CHANGE_TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                goToNextScreen()
            }
        }.start()
        isTimerRunning = true
    }

    fun startAnimation() {
        _lastInputCommand.value = LastInputCommand.PLAY
    }

    fun pauseAnimation() {
        countDownTimer?.cancel()
        isTimerRunning = false
        _lastInputCommand.value = LastInputCommand.PAUSE
    }

    fun resumeAnimation() {
        if (!isTimerRunning) {
            startTimer()
            _lastInputCommand.value = LastInputCommand.RESUME
        }
    }

    fun stopAnimation() {
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false
        remainingTime = SCREEN_CHANGE_INTERVAL
    }

    companion object {
        const val SCREEN_CHANGE_INTERVAL = 4500L
        private const val SCREEN_CHANGE_TIMER_INTERVAL = 100L
    }
}

data class WelcomeScreen(
    val title: Int,
    val description: Int? = null,
    val background: Int,
    val dark: Boolean,
    val repeatAnimation: Boolean = false
)

enum class LastInputCommand {
    PLAY, PAUSE, RESUME
}
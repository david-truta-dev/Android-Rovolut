package com.tdavidc.dev.ui.screens.welcome.views

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ViewStoryBarItemBinding

class StoryBarViewItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewStoryBarItemBinding.inflate(LayoutInflater.from(context), this, true)

    private var animation: ObjectAnimator? = null

    fun setColor(dark: Boolean) {
        val activeColor = context.getColor(if (dark) R.color.white else R.color.black)
        val inactiveColor = context.getColor(if (dark) R.color.gray3 else R.color.gray)
        binding.progressBar.progressTintList = ColorStateList.valueOf(activeColor)
        binding.progressBar.progressBackgroundTintList = ColorStateList.valueOf(inactiveColor)
        binding.progressBar.progress = 0
    }

    fun fill() {
        animation?.cancel()
        binding.progressBar.post { binding.progressBar.progress = 100 }
    }

    fun empty() {
        animation?.cancel()
        binding.progressBar.post { binding.progressBar.progress = 0 }
    }

    fun animate(fillBarDuration: Long) {
        animation?.cancel()
        animation = ObjectAnimator.ofInt(binding.progressBar, "progress", 100).apply {
            duration = fillBarDuration
            interpolator = LinearInterpolator()
            start()
        }
    }

    fun pause() {
        animation?.pause()
    }

    fun resume() {
        animation?.resume()
    }
}
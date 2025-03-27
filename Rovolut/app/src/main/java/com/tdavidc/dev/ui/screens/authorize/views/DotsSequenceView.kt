package com.tdavidc.dev.ui.screens.authorize.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ViewDotsSequenceBinding
import com.tdavidc.dev.utility.extensions.getDuration

class DotsSequenceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewDotsSequenceBinding.inflate(LayoutInflater.from(context), this, true)

    fun highlightDot(index: Int) {
        (binding.linearLayout.children.toList().getOrNull(index) as? ImageView?)?.let { imgView ->
            imgView.setColorFilter(context.getColor(R.color.white))
            animateScale(imgView, "scaleX")
            animateScale(imgView, "scaleY")
        }
    }

    fun unHighlightDot(index: Int) {
        (binding.linearLayout.children.toList().getOrNull(index) as? ImageView?)?.let { imgView ->
            animateColor(imgView, R.color.white, R.color.gray2)
        }
    }

    fun unHighlightAllDots() {
        binding.linearLayout.children.forEach { dotView ->
            (dotView as? ImageView)?.let {
                animateColor(it, R.color.white, R.color.gray2)
            }
        }
        ObjectAnimator.ofFloat(
            this,
            "translationX",
            0f, 40f, -40f, 40f, -40f, 40f, -40f, 0f
        ).apply {
            duration = context.resources.getDuration(R.integer.anim_duration_longer)
            start()
        }
    }

    private fun animateScale(view: ImageView, scaleProperty: String) {
        ObjectAnimator.ofFloat(view, scaleProperty, 1f, 1.25f).apply {
            duration = context.resources.getDuration(R.integer.anim_duration_short) / 2
            doOnEnd {
                ObjectAnimator.ofFloat(view, scaleProperty, 1.25f, 1f).apply {
                    duration = context.resources.getDuration(R.integer.anim_duration_short) / 2
                    start()
                }
            }
            start()
        }
    }

    private fun animateColor(view: ImageView, startColorResId: Int, endColorResId: Int) {
        ValueAnimator.ofArgb(context.getColor(startColorResId), context.getColor(endColorResId))
            .apply {
                duration = context.resources.getDuration(R.integer.anim_duration_short)
                addUpdateListener { animator ->
                    view.setColorFilter(animator.animatedValue as Int)
                }
                start()
            }
    }
}
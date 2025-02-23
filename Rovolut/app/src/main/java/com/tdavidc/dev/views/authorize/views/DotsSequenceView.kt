package com.tdavidc.dev.views.authorize.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ViewDotsSequenceBinding
import com.tdavidc.dev.views.base.BaseView

class DotsSequenceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseView<ViewDotsSequenceBinding>(
    context,
    attrs,
    defStyleAttr,
    ViewDotsSequenceBinding::inflate
) {

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
    }

    private fun animateScale(view: ImageView, scaleProperty: String) {
        ObjectAnimator.ofFloat(view, scaleProperty, 1f, 1.25f).apply {
            duration = ANIMATION_DURATION / 2
            doOnEnd {
                ObjectAnimator.ofFloat(view, scaleProperty, 1.25f, 1f).apply {
                    duration = ANIMATION_DURATION / 2
                    start()
                }
            }
            start()
        }
    }

    private fun animateColor(view: ImageView, startColorResId: Int, endColorResId: Int) {
        val startColor = context.getColor(startColorResId)
        val endColor = context.getColor(endColorResId)

        ValueAnimator.ofArgb(startColor, endColor).apply {
            duration = ANIMATION_DURATION
            addUpdateListener { animator ->
                view.setColorFilter(animator.animatedValue as Int)
            }
            start()
        }
    }

    companion object {
        const val ANIMATION_DURATION = 150L
    }
}
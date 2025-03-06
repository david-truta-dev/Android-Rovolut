package com.tdavidc.dev.views.welcome.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class StoryBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var fillBarDuration: Long = 0

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL
    }

    fun setup(barCount: Int, duration: Long, currentBar: Int = 0, dark: Boolean = false) {
        this.fillBarDuration = duration
        removeAllViews()
        for (i in 0 until barCount) {
            val barView = StoryBarViewItem(context).apply {
                layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
                    weight = 1f
                }
            }
            addView(barView)
        }
        setCurrentBar(currentBar, dark)
    }

    fun setCurrentBar(currentBar: Int, dark: Boolean) {
        for (i in 0 until childCount) {
            val barView = getChildAt(i) as StoryBarViewItem
            barView.setColor(dark)
            if (i < currentBar) {
                barView.fill()
            } else if (i == currentBar) {
                barView.animate(fillBarDuration)
            } else { // i > currentBar
                barView.empty()
            }
        }
    }

    fun pause(currentBar: Int) {
        val barView = getChildAt(currentBar) as? StoryBarViewItem
        barView?.pause()
    }

    fun resume(currentBar: Int) {
        val barView = getChildAt(currentBar) as? StoryBarViewItem
        barView?.resume()
    }
}
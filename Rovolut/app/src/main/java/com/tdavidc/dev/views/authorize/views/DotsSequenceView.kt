package com.tdavidc.dev.views.authorize.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
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
        (binding.linearLayout.children.toList().getOrNull(index) as? ImageView?)?.setColorFilter(
            context.getColor(
                R.color.white
            )
        )
    }

    fun unHighlightDot(index: Int) {
        (binding.linearLayout.children.toList().getOrNull(index) as? ImageView?)?.colorFilter = null
    }

    fun unHighlightAllDots() {
        binding.linearLayout.children.forEach {
            (it as? ImageView?)?.setColorFilter(context.getColor(R.color.gray))
        }
    }
}
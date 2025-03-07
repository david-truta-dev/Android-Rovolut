package com.tdavidc.dev.views.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding

@SuppressLint("ViewConstructor")
open class BaseView<T : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    inflateBinding: (inflater: LayoutInflater) -> T
) : FrameLayout(context, attrs, defStyleAttr) {

    // ViewBinding instance
    protected val binding: T = inflateBinding(LayoutInflater.from(context))

    init {
        this.addView(binding.root)
    }
}
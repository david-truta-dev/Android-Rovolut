package com.tdavidc.dev.views.main.views

import android.content.Context
import android.util.AttributeSet
import com.tdavidc.dev.databinding.ViewTopNavigationBarBinding
import com.tdavidc.dev.views.base.BaseView

class TopNavigationBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseView<ViewTopNavigationBarBinding>(
    context,
    attrs,
    defStyleAttr,
    ViewTopNavigationBarBinding::inflate
) {
    fun setOnClickProfilePicture(onClick: () -> Unit) {
        binding.profilePicture.setOnClickListener { onClick() }
    }
}
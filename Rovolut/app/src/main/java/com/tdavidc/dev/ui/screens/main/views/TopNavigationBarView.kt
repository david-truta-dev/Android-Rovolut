package com.tdavidc.dev.ui.screens.main.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.tdavidc.dev.databinding.ViewTopNavigationBarBinding

class TopNavigationBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewTopNavigationBarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnClickProfilePicture(onClick: () -> Unit) {
        binding.profilePicture.setOnClickListener { onClick() }
    }
}
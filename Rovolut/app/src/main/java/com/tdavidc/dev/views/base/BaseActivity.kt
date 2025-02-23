package com.tdavidc.dev.views.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    private var loadingCounter: Int = 0
    private lateinit var loadingOverlay: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = inflateView()

        setContentView(setupViewWithLoading())

        bindViewModel()
    }

    //Implement this method to inflate the view
    protected abstract fun inflateView(): T

    // Implement this method to bind the viewModel data to the view
    protected open fun bindViewModel() {}


    fun showLoading() {
        if (++loadingCounter > 0) {
            loadingOverlay.isVisible = true
        }
    }

    fun hideLoading() {
        loadingCounter = maxOf(--loadingCounter, 0)
        if(loadingCounter == 0) {
            loadingOverlay.isVisible = false
        }
    }

    private fun setupViewWithLoading(): FrameLayout {
        val rootLayout = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Add the activity's main view
        rootLayout.addView(binding.root)

        // Add a loading ProgressBar
        loadingOverlay = ProgressBar(this).apply {
            isIndeterminate = true
            visibility = View.GONE // Initially hidden
        }

        // Center the ProgressBar
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.CENTER
        }

        rootLayout.addView(loadingOverlay, params)

        return  rootLayout
    }
}
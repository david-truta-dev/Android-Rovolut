package com.tdavidc.dev.ui.common.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    private var loadingCounter: Int = 0
    private lateinit var loadingOverlay: ProgressBar

    protected var handleInsets: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        handleEdgeToEdge()

        super.onCreate(savedInstanceState)
        binding = inflateView()
        setContentView(setupViewWithLoading())

        handleSystemInsets()
        bindViewModel()
    }

    //Implement this method to inflate the view
    protected abstract fun inflateView(): T

    // Implement this method to bind the viewModel data to the view
    protected open fun bindViewModel() {}

    private fun handleEdgeToEdge() {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
    }

    private fun handleSystemInsets() {
        if (handleInsets) {
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            or WindowInsetsCompat.Type.displayCutout()
                ).also { v.updatePadding(top = it.top, bottom = it.bottom) }
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    fun showLoading() {
        if (++loadingCounter > 0) {
            loadingOverlay.isVisible = true
        }
    }

    fun hideLoading() {
        loadingCounter = maxOf(--loadingCounter, 0)
        if (loadingCounter == 0) {
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
            visibility = View.GONE
        }

        // Center the ProgressBar
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.CENTER
        }

        rootLayout.addView(loadingOverlay, params)

        return rootLayout
    }
}
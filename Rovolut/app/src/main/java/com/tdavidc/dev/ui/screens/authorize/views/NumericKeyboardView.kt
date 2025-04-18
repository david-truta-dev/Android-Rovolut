package com.tdavidc.dev.ui.screens.authorize.views

import android.content.Context
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.view.children
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.ViewNumericKeyboardBinding

class NumericKeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = ViewNumericKeyboardBinding.inflate(LayoutInflater.from(context), this, true)

    private var onDigitClicked: ((String) -> Unit)? = null
    private var onBackspaceClicked: (() -> Unit)? = null

    init {
        binding.numericKeyboardLayout.children.forEach { view ->
            if (view is Button) {
                view.setOnClickListener {
                    it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onDigitClicked?.invoke(view.text.toString())
                }
            }
        }
        binding.keyboardBottomRightBtn.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            onBackspaceClicked?.invoke()
        }
    }

    fun setOnDigitClickListener(onClick: (digit: String) -> Unit) {
        onDigitClicked = onClick
    }

    fun setOnBackspaceClickListener(onClick: () -> Unit) {
        onBackspaceClicked = onClick
    }

    fun setBottomRightButton(icon: Int, onClick: () -> Unit) {
        binding.apply {
            keyboardBottomRightBtn.setImageResource(icon)
            keyboardBottomRightBtn.setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                onClick()
            }
        }
    }

    fun resetBottomRightButton() {
        binding.apply {
            keyboardBottomRightBtn.setImageResource(R.drawable.ic_backspace)
            keyboardBottomRightBtn.setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                onBackspaceClicked?.invoke()
            }
        }
    }

    fun disable() {
        binding.numericKeyboardLayout.children.forEach { view ->
            if (view is Button) {
                view.isEnabled = false
            } else if (view is ImageButton) {
                (view).isEnabled = false
            }
        }
    }

    fun enable() {
        binding.numericKeyboardLayout.children.forEach { view ->
            if (view is Button) {
                view.isEnabled = true
            } else if (view is ImageButton) {
                (view).isEnabled = true
            }
        }
    }
}
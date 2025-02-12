package com.tdavidc.dev.views.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateView()
        setContentView(binding.root)

        bindViewModel()
    }

    //Implement this method to inflate the view
    protected abstract fun inflateView(): T

    // Implement this method to bind the viewModel data to the view
    protected open fun bindViewModel() {}
}
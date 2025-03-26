package com.tdavidc.dev.ui.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tdavidc.dev.databinding.FragmentRevpointsBinding
import com.tdavidc.dev.ui.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RovPointsFragment : BaseFragment() {

    private lateinit var binding: FragmentRevpointsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRevpointsBinding.inflate(inflater, container, false)
        return binding.root
    }

}
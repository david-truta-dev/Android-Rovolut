package com.tdavidc.dev.ui.screens.main.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tdavidc.dev.databinding.FragmentInvestBinding
import com.tdavidc.dev.ui.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvestFragment : BaseFragment() {

    private lateinit var binding: FragmentInvestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvestBinding.inflate(inflater, container, false)
        return binding.root
    }
}
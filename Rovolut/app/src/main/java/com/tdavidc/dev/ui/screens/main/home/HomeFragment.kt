package com.tdavidc.dev.ui.screens.main.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.doOnLayout
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tdavidc.dev.R
import com.tdavidc.dev.databinding.FragmentHomeBinding
import com.tdavidc.dev.ui.common.base.BaseFragment
import com.tdavidc.dev.ui.screens.main.home.adapters.AccountActionItem
import com.tdavidc.dev.ui.screens.main.home.adapters.AccountsPagerAdapter
import com.tdavidc.dev.ui.screens.main.home.views.AccountAction
import com.tdavidc.dev.utility.extensions.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.accountViewPager.adapter = AccountsPagerAdapter(
            listOf(
                AccountActionItem(
                    balance = "lei 152",
                    accountCurrencyImg = R.drawable.ic_app_icon,
                    accountInfo = "Personal · RON",
                    firstAction = AccountAction({
                        Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show()
                    }, "Add money", R.drawable.ic_add),
                    secondAction = AccountAction({
                        Toast.makeText(context, "Move clicked", Toast.LENGTH_SHORT).show()
                    }, "Move", R.drawable.ic_payment),
                    thirdAction = AccountAction({
                        Toast.makeText(context, "Details clicked", Toast.LENGTH_SHORT).show()
                    }, "Details", R.drawable.ic_bank),
                    fourthAction = AccountAction({
                        Toast.makeText(context, "More clicked", Toast.LENGTH_SHORT).show()
                    }, "More", R.drawable.ic_more)
                ),
                AccountActionItem(
                    balance = "lei 0",
                    accountCurrencyImg = R.drawable.ic_bank,
                    accountInfo = "Joint · RON",
                    firstAction = AccountAction({
                        Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show()
                    }, "Add money", R.drawable.ic_add),
                    secondAction = AccountAction({
                        Toast.makeText(context, "Move clicked", Toast.LENGTH_SHORT).show()
                    }, "Move", R.drawable.ic_payment),
                    thirdAction = AccountAction({
                        Toast.makeText(context, "Details clicked", Toast.LENGTH_SHORT).show()
                    }, "Details", R.drawable.ic_bank),
                    fourthAction = AccountAction({
                        Toast.makeText(context, "More clicked", Toast.LENGTH_SHORT).show()
                    }, "More", R.drawable.ic_more)
                ),
                AccountActionItem(
                    balance = "€ 69",
                    accountCurrencyImg = R.drawable.ic_search,
                    accountInfo = "Personal · EUR",
                    firstAction = AccountAction({
                        Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show()
                    }, "Add money", R.drawable.ic_add),
                    secondAction = AccountAction({
                        Toast.makeText(context, "Move clicked", Toast.LENGTH_SHORT).show()
                    }, "Move", R.drawable.ic_payment),
                    thirdAction = AccountAction({
                        Toast.makeText(context, "Details clicked", Toast.LENGTH_SHORT).show()
                    }, "Details", R.drawable.ic_bank),
                    fourthAction = AccountAction({
                        Toast.makeText(context, "More clicked", Toast.LENGTH_SHORT).show()
                    }, "More", R.drawable.ic_more)
                ),
            ),
        ) {
            Toast.makeText(context, "Accounts clicked", Toast.LENGTH_SHORT).show()
        }

        binding.accountViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.fakeLst.gravity = Gravity.START
                    1 -> binding.fakeLst.gravity = Gravity.CENTER
                    2 -> binding.fakeLst.gravity = Gravity.END
                }
            }
        })

        binding.accountViewPager.doOnLayout {
            binding.accountViewPager[binding.accountViewPager.currentItem].findViewById<LinearLayout>(
                R.id.buttons_container
            ).doOnLayout {
                //TODO: replace this with a custom indicator
                val tabL = TabLayout(requireContext())

                tabL.isClickable = false
                tabL.isEnabled = false
                tabL.layoutParams =
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = it.measuredHeight + 24.dp
                    }

                binding.tabLayoutContainer.addView(tabL)

                TabLayoutMediator(tabL, binding.accountViewPager) { _, _ -> }.attach()
            }
        }

        return binding.root
    }
}



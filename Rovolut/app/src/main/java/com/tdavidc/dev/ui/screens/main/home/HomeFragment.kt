package com.tdavidc.dev.ui.screens.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.doOnLayout
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
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

        positionPagerIndicatorCorrectly()
        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {
        binding.accountViewPager.adapter = AccountsPagerAdapter(
            listOf(
                mockItem1,
                mockItem2,
                mockItem3,
                mockItem1,
                mockItem2,
                mockItem3,
                mockItem1,
                mockItem2,
                mockItem3
            ),
        ) {
            Toast.makeText(context, "Accounts clicked", Toast.LENGTH_SHORT).show()
        }

        binding.scrollingIndicator.attachToPager(binding.accountViewPager)
        binding.accountViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //TODO: change fragment content
            }
        })
    }

    private fun positionPagerIndicatorCorrectly() {
        binding.accountViewPager.post {
            binding.accountViewPager[0]
                .findViewById<LinearLayout>(R.id.buttons_container)?.doOnLayout {
                    binding.scrollingIndicator.layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = it.measuredHeight + 36.dp
                    }
                }
        }
    }

    private val mockItem1 = AccountActionItem(
        balance = "lei 152",
        accountCurrencyImg = R.drawable.ic_app_icon,
        accountInfo = "Personal · RON",
        firstAction = AccountAction(
            { Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show() },
            "Add money", R.drawable.ic_add,
        ),
        secondAction = AccountAction(
            { Toast.makeText(context, "Move clicked", Toast.LENGTH_SHORT).show() },
            "Move", R.drawable.ic_payment,
        ),
        thirdAction = AccountAction(
            { Toast.makeText(context, "Details clicked", Toast.LENGTH_SHORT).show() },
            "Details", R.drawable.ic_bank,
        ),
        fourthAction = AccountAction(
            { Toast.makeText(context, "More clicked", Toast.LENGTH_SHORT).show() },
            "More", R.drawable.ic_more,
        ),
    )
    private val mockItem2 = AccountActionItem(
        balance = "lei 420",
        accountCurrencyImg = R.drawable.ic_bank,
        accountInfo = "Joint · RON",
        firstAction = AccountAction(
            { Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show() },
            "Add money", R.drawable.ic_add,
        ),
        secondAction = AccountAction(
            { Toast.makeText(context, "Move clicked", Toast.LENGTH_SHORT).show() },
            "Move", R.drawable.ic_payment,
        ),
        thirdAction = AccountAction(
            { Toast.makeText(context, "Details clicked", Toast.LENGTH_SHORT).show() },
            "Details", R.drawable.ic_bank,
        ),
        fourthAction = AccountAction(
            { Toast.makeText(context, "More clicked", Toast.LENGTH_SHORT).show() },
            "More", R.drawable.ic_more,
        ),
    )
    private val mockItem3 = AccountActionItem(
        balance = "€ 69",
        accountCurrencyImg = R.drawable.ic_search,
        accountInfo = "Personal · EUR",
        firstAction = AccountAction({
            Toast.makeText(context, "Add clicked", Toast.LENGTH_SHORT).show()
        }, "Add money", R.drawable.ic_add),
        secondAction = AccountAction({
            Toast.makeText(
                context,
                "Move clicked",
                Toast.LENGTH_SHORT
            ).show()
        }, "Move", R.drawable.ic_payment),
        thirdAction = AccountAction({
            Toast.makeText(
                context,
                "Details clicked",
                Toast.LENGTH_SHORT
            ).show()
        }, "Details", R.drawable.ic_bank),
        fourthAction = AccountAction({
            Toast.makeText(
                context,
                "More clicked",
                Toast.LENGTH_SHORT
            ).show()
        }, "More", R.drawable.ic_more)
    )
}

package com.tdavidc.dev.ui.screens.main.home.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.tdavidc.dev.databinding.ViewAccountActionsBinding
import com.tdavidc.dev.ui.screens.main.home.adapters.AccountActionItem

class AccountActionsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding =
        ViewAccountActionsBinding.inflate(LayoutInflater.from(context), this, true)

    fun setup(
        accountActionItem: AccountActionItem,
        onAccountsClick: () -> Unit
    ) {
        binding.currencyImage.setImageResource(accountActionItem.accountCurrencyImg)
        binding.accountInfo.text = accountActionItem.accountInfo
        binding.balanceText.text = accountActionItem.balance
        binding.accountsButton.setOnClickListener { onAccountsClick() }
        setAccountAction(1, accountActionItem.firstAction)
        setAccountAction(2, accountActionItem.secondAction)
        setAccountAction(3, accountActionItem.thirdAction)
        setAccountAction(4, accountActionItem.fourthAction)
    }

    private fun setAccountAction(number: Int, action: AccountAction) {
        val iconBtn = when (number) {
            1 -> binding.firstActionIconBtn
            2 -> binding.secondActionIconBtn
            3 -> binding.thirdActionIconBtn
            4 -> binding.fourthActionIconBtn
            else -> null
        }
        val text = when (number) {
            1 -> binding.firstActionText
            2 -> binding.secondActionText
            3 -> binding.thirdActionText
            4 -> binding.fourthActionText
            else -> null
        }
        val container = when (number) {
            1 -> binding.firstAction
            2 -> binding.secondAction
            3 -> binding.thirdAction
            4 -> binding.fourthAction
            else -> null
        }
        iconBtn?.setOnClickListener { action.onClick() }
        iconBtn?.setImageResource(action.icon)
        text?.text = action.text
        container?.setOnClickListener {
            iconBtn?.performClick();
            iconBtn?.isPressed = true;
            iconBtn?.invalidate();
            iconBtn?.isPressed = false;
            iconBtn?.invalidate();
        }
    }

}

data class AccountAction(
    val onClick: () -> Unit,
    val text: String,
    @DrawableRes val icon: Int
)
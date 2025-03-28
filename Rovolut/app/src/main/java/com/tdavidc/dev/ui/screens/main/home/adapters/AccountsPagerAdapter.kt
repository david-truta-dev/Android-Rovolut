package com.tdavidc.dev.ui.screens.main.home.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tdavidc.dev.ui.screens.main.home.views.AccountAction
import com.tdavidc.dev.ui.screens.main.home.views.AccountActionsView

class AccountsPagerAdapter(
    private val accountActions: List<AccountActionItem>,
    private val onAccountsClick: () -> Unit
) : RecyclerView.Adapter<AccountsPagerAdapter.Page2ViewHolder>() {

    inner class Page2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Page2ViewHolder =
        Page2ViewHolder(AccountActionsView(parent.context).apply({
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }))

    override fun getItemCount(): Int = accountActions.size

    override fun onBindViewHolder(holder: Page2ViewHolder, position: Int) {
        val view = holder.itemView
        val castView = view as? AccountActionsView
        castView?.apply {
            setup(accountActions[position], onAccountsClick)
        }
    }
}

data class AccountActionItem(
    val balance: String,
    val accountInfo: String,
    val accountCurrencyImg: Int,
    val firstAction: AccountAction,
    val secondAction: AccountAction,
    val thirdAction: AccountAction,
    val fourthAction: AccountAction
)
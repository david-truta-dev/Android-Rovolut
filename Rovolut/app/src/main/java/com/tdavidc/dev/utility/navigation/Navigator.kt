package com.tdavidc.dev.utility.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.view.authorize.AuthorizeActivity
import com.tdavidc.dev.ui.view.login.LoginActivity
import com.tdavidc.dev.ui.view.main.MainActivity
import com.tdavidc.dev.ui.view.welcome.WelcomeActivity

object Navigator {

    fun goToWelcomeActivity(context: AppCompatActivity) {
        Intent(context, WelcomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            context.startActivity(it)
            context.overridePendingTransition(R.anim.slide_from_bottom, R.anim.fade_out)
        }
    }

    fun goToLogin(context: AppCompatActivity) {
        Intent(context, LoginActivity::class.java).also {
            context.startActivity(it)
        }
    }

    fun goToCreateAccount(context: AppCompatActivity) {
        //TODO: modify this to create account
        goToLogin(context)
    }

    fun goToAuthorize(context: AppCompatActivity) {
        Intent(context, AuthorizeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            context.startActivity(it)
            context.overridePendingTransition(R.anim.slide_from_bottom, R.anim.fade_out)
        }
    }

    fun goToMainActivity(context: AppCompatActivity) {
        Intent(context, MainActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { intent ->
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.slide_from_bottom, R.anim.fade_out)
        }
    }

}
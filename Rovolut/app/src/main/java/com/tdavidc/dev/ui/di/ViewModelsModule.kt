package com.tdavidc.dev.ui.di

import com.tdavidc.dev.data.repository.AuthRepository
import com.tdavidc.dev.ui.screens.LauncherViewModel
import com.tdavidc.dev.ui.screens.login.LoginViewModel
import com.tdavidc.dev.ui.screens.main.MainViewModel
import com.tdavidc.dev.ui.screens.profile.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelsModule {
    @Provides
    @ViewModelScoped
    fun provideMainViewModel(): MainViewModel = MainViewModel()

    @Provides
    @ViewModelScoped
    fun provideLoginViewModel(authRepository: AuthRepository): LoginViewModel =
        LoginViewModel(authRepository)

    @Provides
    @ViewModelScoped
    fun provideLauncherViewModel(authRepository: AuthRepository): LauncherViewModel =
        LauncherViewModel(authRepository)

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(authRepository: AuthRepository): ProfileViewModel =
        ProfileViewModel(authRepository)
}

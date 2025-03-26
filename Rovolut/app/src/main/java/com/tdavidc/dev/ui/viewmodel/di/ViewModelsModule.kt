package com.tdavidc.dev.ui.viewmodel.di

import com.tdavidc.dev.data.repository.AuthRepository
import com.tdavidc.dev.ui.viewmodel.LauncherViewModel
import com.tdavidc.dev.ui.viewmodel.login.LoginViewModel
import com.tdavidc.dev.ui.viewmodel.main.MainViewModel
import com.tdavidc.dev.ui.viewmodel.profile.ProfileViewModel
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

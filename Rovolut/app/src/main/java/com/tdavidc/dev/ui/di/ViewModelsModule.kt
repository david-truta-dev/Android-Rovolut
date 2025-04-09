package com.tdavidc.dev.ui.di

import com.tdavidc.dev.data.repository.auth.AuthRepository
import com.tdavidc.dev.ui.screens.LauncherViewModel
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
    fun provideLauncherViewModel(authRepository: AuthRepository): LauncherViewModel =
        LauncherViewModel(authRepository)

}

package com.tdavidc.dev.viewmodels.di

import com.tdavidc.dev.viewmodels.main.MainViewModel
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
}

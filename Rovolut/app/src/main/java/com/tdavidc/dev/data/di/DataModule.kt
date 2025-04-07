package com.tdavidc.dev.data.di

import com.tdavidc.dev.data.repository.auth.AuthRepository
import com.tdavidc.dev.data.repository.user.UserRepository
import com.tdavidc.dev.data.source.local.DataStoreLocalStorage
import com.tdavidc.dev.data.source.remote.service.AuthService
import com.tdavidc.dev.data.source.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthService,
        localStorage: DataStoreLocalStorage
    ): AuthRepository = AuthRepository(authService, localStorage)

    @Provides
    @Singleton
    fun provideUserRepository(
        userService: UserService,
        localStorage: DataStoreLocalStorage
    ): UserRepository = UserRepository(userService, localStorage)
}
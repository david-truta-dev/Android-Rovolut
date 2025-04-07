package com.tdavidc.dev.data.repository.user

import com.tdavidc.dev.data.source.model.User
import io.reactivex.rxjava3.core.Single

interface IUserRepository {
    fun getUserData(): Single<User>
}
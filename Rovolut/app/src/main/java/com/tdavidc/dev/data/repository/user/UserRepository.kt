package com.tdavidc.dev.data.repository.user

import com.tdavidc.dev.data.source.local.ILocalStorage
import com.tdavidc.dev.data.source.model.User
import com.tdavidc.dev.data.source.remote.service.UserService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val localStorage: ILocalStorage
) : IUserRepository {

    override fun getUserData(): Single<User> =  userService.getUser()

}
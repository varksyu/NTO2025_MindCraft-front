package ru.myitschool.work.data.auth

import ru.myitschool.work.data.user.UserDto
import ru.myitschool.work.domain.auth.AuthRepo

class AuthRepoImpl(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authStorageDataSource: AuthStorageDataSource
) : AuthRepo {

    override suspend fun login(email: String, password: String): Result<UserDto> {
        val token = authStorageDataSource.updateToken(email, password)

        val userInfo = authNetworkDataSource.login(token).onFailure {
            authStorageDataSource.clear()
        }
        if (userInfo.isSuccess){
            authStorageDataSource.updateUserInfo(userInfo)
        }

        return userInfo
    }

}
package ru.myitschool.work.domain.user

import ru.myitschool.work.data.auth.AuthStorageDataSource
import ru.myitschool.work.data.user.UserDto

class GetUserUseCase(
    private val repo: UserRepo,
    private val authStorageDataSource: AuthStorageDataSource
) {
    private fun getUserFromStorage() : UserDto? {
        return authStorageDataSource.userInfo
    }
    suspend operator fun invoke(user_login : String?) = repo.getUser(user_login
        ?: getUserFromStorage()?.login!!)
    suspend fun getEntrancesList() = repo.getEntrancesList(getUserFromStorage()?.login!!)

}
package ru.myitschool.work.domain.user

import ru.myitschool.work.data.auth.AuthStorageDataSource
import ru.myitschool.work.data.user.UserDto

class EnterUseCase (
    private val repo: UserRepo,
    private val authStorageDataSource : AuthStorageDataSource
) {
    private fun getUserFromStorage() : UserDto? {
        return authStorageDataSource.userInfo
    }
    suspend operator fun invoke(value : Long) = repo.enter(value, getUserFromStorage()?.login!!)
}
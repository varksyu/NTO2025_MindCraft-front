package ru.myitschool.work.domain.auth

import ru.myitschool.work.data.user.UserDto

interface AuthRepo {
    //suspend fun isUserExist(email: String): Result<Boolean?>
    suspend fun login(login: String, password: String) : Result <UserDto>
}

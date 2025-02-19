package ru.myitschool.work.domain.auth

import ru.myitschool.work.data.user.UserDto

class LoginUseCase(
    private val authRepo : AuthRepo
){
    suspend operator fun invoke(login : String, password : String) : Result<UserDto> {
        return authRepo.login(login, password)
    }
}
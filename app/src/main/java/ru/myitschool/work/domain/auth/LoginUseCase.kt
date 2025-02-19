package ru.myitschool.work.domain.auth

import ru.myitschool.work.data.user.UserDto

class LoginUseCase(
    private val authRepo : AuthRepo
){
    suspend operator fun invoke(email : String, password : String) : Result<UserDto> {
        return authRepo.login(email, password)
    }
}
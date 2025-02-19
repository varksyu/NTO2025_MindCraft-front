package ru.myitschool.work.domain.user


interface UserRepo {
    suspend fun getUser(login: String) : Result<UserEntity>
    suspend fun getEntrancesList(login : String) : Result<List<EntranceEntity>>
}
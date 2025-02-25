package ru.myitschool.work.domain.user


interface UserRepo {
    suspend fun getUser(login: String) : Result<UserEntity>
    suspend fun getEntrancesList(login : String) : Result<List<EntranceEntity>>
    suspend fun enter(value : String, login : String) : Result<Boolean>
    suspend fun block(login: String): Result<Boolean>
    suspend fun unblock(login: String): Result<Boolean>
}
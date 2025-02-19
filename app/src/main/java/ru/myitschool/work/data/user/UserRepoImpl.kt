package ru.myitschool.work.data.user
import ru.myitschool.work.domain.user.EntranceEntity
import ru.myitschool.work.domain.user.UserEntity
import ru.myitschool.work.domain.user.UserRepo


class UserRepoImpl (
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepo {
    override suspend fun getUser(login: String): Result<UserEntity> {
        return userNetworkDataSource.getUser(login).map { dto ->
            UserEntity(
                id = dto.id ?: -1,
                name = dto.name,
                avatarUrl = dto.avatarUrl ?: "",
                authorities = dto.authorities,
                login = login,
                position = dto.position,
                lastEntry = dto.lastEntry ?: "",
            )

            }
        }


    override suspend fun getEntrancesList(login : String): Result<List<EntranceEntity>> {
        return userNetworkDataSource.getEntrancesList(login).map { userList ->
            userList.map { it.toEntity() }
        }
    }
    fun EntranceDto.toEntity(): EntranceEntity {
        return EntranceEntity(
            login = login,
            name = this.name,
            enteredAt = this.enteredAt,
            enterType = this.enterType
        )
    }
}
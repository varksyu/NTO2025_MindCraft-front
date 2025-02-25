package ru.myitschool.work.data.user
import ru.myitschool.work.domain.user.EntranceEntity
import ru.myitschool.work.domain.user.UserEntity
import ru.myitschool.work.domain.user.UserRepo
import kotlin.contracts.Returns
import kotlin.math.log


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
                access = dto.access
            )

            }
        }


    override suspend fun getEntrancesList(login : String): Result<List<EntranceEntity>> {
        return userNetworkDataSource.getEntrancesList(login).map { userList ->
            userList.map { it.toEntity() }
        }
    }

    override suspend fun enter(value: String, login : String): Result<Boolean> {
        return userNetworkDataSource.enter(value, login);
    }

    fun EntranceDto.toEntity(): EntranceEntity {
        return EntranceEntity(
            login = login,
            name = this.name,
            enteredAt = this.enteredAt,

            enterType = this.enterType
        )
    }

    override suspend fun block(login: String): Result<Boolean> {
        return userNetworkDataSource.block(login);
    }

    override suspend fun unblock(login: String): Result<Boolean> {
        return userNetworkDataSource.block(login);
    }
}
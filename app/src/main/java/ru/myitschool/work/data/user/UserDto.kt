package ru.myitschool.work.data.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.myitschool.work.domain.user.UserEntity
import java.sql.Timestamp

@Serializable
data class UserDto(
    @SerialName("id")
    val id : Long?,
    @SerialName("login")
    var login: String,
    @SerialName("name")
    var name: String,
    @SerialName("avatarUrl")
    var avatarUrl: String?,
    @SerialName("position")
    val position: String,
    @SerialName("lastEntry")
    val lastEntry : String,
    @SerialName("authorities")
    val authorities : String
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = id ?: throw IllegalArgumentException("User ID cannot be null"),
            login,
            name = name,
            avatarUrl = avatarUrl,
            position = position,
            lastEntry = lastEntry,
            authorities = authorities
        )
    }
}

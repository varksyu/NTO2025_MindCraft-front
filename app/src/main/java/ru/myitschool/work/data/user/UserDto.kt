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
    @SerialName("lastEnter")
    val lastEntry : String? = null,
    @SerialName("access")
    val access : Boolean,
    @SerialName("authorities")
    val authorities : String
) {
}

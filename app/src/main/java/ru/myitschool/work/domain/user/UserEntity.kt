package ru.myitschool.work.domain.user

import kotlinx.serialization.Serializable
@Serializable
data class UserEntity(
    val id : Long,
    var login: String,
    var name: String,
    var avatarUrl: String?,
    val position : String,
    var lastEntry : String?,
    val authorities : String

)

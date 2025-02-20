package ru.myitschool.work.domain.user

import kotlinx.serialization.Serializable
@Serializable
data class UserEntity(
    val id : Long,
    val login: String,
    val name: String,
    val avatarUrl: String?,
    val position : String,
    val lastEntry : String?,
    val access : Boolean,
    val authorities : String

)

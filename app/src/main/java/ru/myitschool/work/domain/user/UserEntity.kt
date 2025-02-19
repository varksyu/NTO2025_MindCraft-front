package ru.myitschool.work.domain.user

import java.sql.Timestamp

data class UserEntity(
    val id : Long,
    var login: String,
    var name: String,
    var avatarUrl: String?,
    val position : String,
    var lastEntry : String?,
    val authorities : String

)

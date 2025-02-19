package ru.myitschool.work.domain.user

import kotlinx.serialization.Serializable

@Serializable
data class EntranceEntity(
    val login : String,
    var name: String,
    var enteredAt: String,
    var enterType: String,
) {

}

package ru.myitschool.work.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntranceDto(
    @SerialName("login")
    val login : String,
    @SerialName("name")
    var name: String,
    @SerialName("enteredAt")
    var enteredAt: String,
    @SerialName("enterType")
    var enterType: String,
) {

}
package ru.myitschool.work.data.user

import kotlinx.serialization.SerialName

data class EntranceDto(
    @SerialName("login")
    val login : String,
    @SerialName("name")
    var name: String,
    @SerialName("enterAt")
    var enterAt: String,
    @SerialName("enterType")
    var enterType: String,
) {

}
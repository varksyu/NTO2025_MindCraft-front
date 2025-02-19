package ru.myitschool.work.domain.user

import kotlinx.serialization.SerialName

data class EntranceEntity(
    val login : String,
    var name: String,
    var enterAt: String,
    var enterType: String,
) {

}

package ru.myitschool.work.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable;

@Serializable
data class CodeDto(

    @SerialName("value")
    val value : String
)

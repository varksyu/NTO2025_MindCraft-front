package ru.myitschool.work.data.auth

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myitschool.work.core.Constants.SERVER_ADDRESS
import ru.myitschool.work.data.auth.Network.client
import ru.myitschool.work.data.user.UserDto

object AuthNetworkDataSource {

//    suspend fun isUserExist(login: String): Result<Boolean?> = withContext(Dispatchers.IO) {
//        runCatching {
//            val result = client.get("$SERVER_ADDRESS/api/login/$login") //10.0.2.2
//            when (result.status) {
//                HttpStatusCode.OK -> { return@runCatching true }
//                HttpStatusCode.NotFound -> { return@runCatching false }
//                else -> {return@runCatching null }
//            }
//        }
//    }

    suspend fun login(token: String): Result<UserDto> = withContext(Dispatchers.IO) {

        runCatching {
            val result = client.get("$SERVER_ADDRESS/api/login") {
                header(HttpHeaders.Authorization, token)
            }
            Log.d("result", "${result.status}")
            if (result.status == HttpStatusCode.Unauthorized) {
                error("Неверный email или пароль")
            }
            result.body<UserDto>()
        }

    }

}
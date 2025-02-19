package ru.myitschool.work.data.user

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.myitschool.work.core.Constants.SERVER_ADDRESS
import ru.myitschool.work.data.auth.AuthStorageDataSource.token
import ru.myitschool.work.data.auth.Network.client

class UserNetworkDataSource {
    suspend fun getUser(login : String): Result<UserDto> = withContext(Dispatchers.IO) {
        //runCatching {

        val result = client.get("$SERVER_ADDRESS/api/${login}/info") {
            header(HttpHeaders.Authorization, token)
        }

        Log.d("tututuut", "${result.status}")



        if (result.status != HttpStatusCode.OK) {
            error("Status ${result.status}")
        }
        Log.d("result", result.bodyAsText())
        result.body()
       // }
    }

    suspend fun getEntrancesList(login : String) : Result<List<EntranceDto>> = withContext(Dispatchers.IO){
        runCatching {
            val result = client.get("http://$SERVER_ADDRESS/api/$login/entrances") {
                header(HttpHeaders.Authorization, token)
            }
            Log.d("serverCode", "${result.status}")
            if (result.status != HttpStatusCode.OK) {
                error("Status ${result.status}")
            }
            result.body()
        }

    }
}
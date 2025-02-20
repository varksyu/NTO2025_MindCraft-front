package ru.myitschool.work.ui.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.myitschool.work.data.auth.AuthStorageDataSource
import ru.myitschool.work.data.user.UserNetworkDataSource
import ru.myitschool.work.data.user.UserRepoImpl
import ru.myitschool.work.domain.user.BlockUseCase
import ru.myitschool.work.domain.user.EntranceEntity
import ru.myitschool.work.domain.user.GetUserUseCase
import ru.myitschool.work.domain.user.UnBlockUseCase
import ru.myitschool.work.domain.user.UserEntity
import ru.myitschool.work.ui.profile.ProfileViewModel.State

class AdminViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val blockUseCase: BlockUseCase,
    private val unBlockUseCase: UnBlockUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    fun search(
        login : String,
    ) {
        viewModelScope.launch {
            _state.emit(State.Loading)
            val entranceList : List<EntranceEntity> = getUserUseCase.getEntrancesList().fold(
                onSuccess = { list ->
                    list
                },
                onFailure = {
                    emptyList()
                }
            )
            _state.emit(
                getUserUseCase.invoke(login).fold(
                    onSuccess = { data ->
                        Log.d("uraa", "успех успех ${data.toString()}")

                        State.Show(data, entranceList)
                    },
                    onFailure = { error ->
                        Log.d("kaput", error.message.toString())
                        State.Error(error.message.toString())
                    }
                ) as State
            )
            _state.emit(State.Error("Не удалось загрузить профиль"))
        }
    }

//    fun clickRefresh() {
//        updateStateGet()
//    }

//    fun updateStateGet() {
//        viewModelScope.launch {
//            _state.emit(State.Loading)
//            val entranceList : List<EntranceEntity> = getUserUseCase.getEntrancesList().fold(
//                onSuccess = { list ->
//                    list
//                },
//                onFailure = {
//                    emptyList()
//                }
//            )
//            _state.emit(
//                getUserUseCase.invoke().fold(
//                    onSuccess = { data ->
//                        Log.d("uraa", "успех успех ${data.toString()}")
//
//                        State.Show(data, entranceList)
//                    },
//                    onFailure = { error ->
//                        Log.d("kaput", error.message.toString())
//                        State.Error(error.message.toString())
//                    }
//                ) as State
//            )
//            _state.emit(State.Error("о нет ошибка ошибка помогите"))
//        }
//    }

    suspend fun blockUser() {
        blockUseCase.invoke().fold(
            onSuccess = { data ->
                Log.d("uraa", "успех успех ${data.toString()}")
            },
            onFailure = { error ->
                Log.d("kaput", error.message.toString())
                State.Error(error.message.toString())
            }
        )
    }

    suspend fun unblockUser() {
        unBlockUseCase.invoke().fold(
            onSuccess = { data ->
                Log.d("uraa", "успех успех ${data.toString()}")
            },
            onFailure = { error ->
                Log.d("kaput", error.message.toString())
                State.Error(error.message.toString())
            }
        )
    }

    sealed interface State {
        data object Loading: State
        data class Show(
            val profileInfo : UserEntity,
            val entrancesList : List<EntranceEntity>
        ) : State
        data class Error(
            val text: String
        ) : State
    }
    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AdminViewModel(
                    getUserUseCase = GetUserUseCase(
                        repo = UserRepoImpl(
                            userNetworkDataSource = UserNetworkDataSource()
                        ),
                        authStorageDataSource = AuthStorageDataSource
                    ),
                    blockUseCase = BlockUseCase(repo = UserRepoImpl(
                        userNetworkDataSource = UserNetworkDataSource()
                    ),
                        authStorageDataSource = AuthStorageDataSource
                    ),
                    unBlockUseCase = UnBlockUseCase(repo = UserRepoImpl(
                        userNetworkDataSource = UserNetworkDataSource()
                    ),
                        authStorageDataSource = AuthStorageDataSource
                    )
                ) as T
            }
        }
    }

}
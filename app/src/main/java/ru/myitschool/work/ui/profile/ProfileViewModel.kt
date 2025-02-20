package ru.myitschool.work.ui.profile

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
import ru.myitschool.work.domain.user.EntranceEntity
import ru.myitschool.work.domain.user.GetUserUseCase
import ru.myitschool.work.domain.user.UserEntity


class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        updateStateGet()
    }

    fun clickRefresh() {
        updateStateGet()
    }

    fun updateStateGet() {
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
                getUserUseCase.invoke(null).fold(
                    onSuccess = { data ->
                        Log.d("uraa", "успех успех ${data.toString()}")
                        State.Show(data, entranceList)
                    },
                    onFailure = { error ->
                        Log.d("kaput", error.message.toString())
                        State.Error(error.message.toString())
                    }
                )
            )
            //_state.emit(State.Error("о нет ошибка ошибка помогите"))
        }
    }



    sealed interface State {
        data object Loading: State
        data class Show(
            val profileInfo: UserEntity,
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
                return ProfileViewModel(
                    getUserUseCase = GetUserUseCase(
                        repo = UserRepoImpl(
                            userNetworkDataSource = UserNetworkDataSource()
                        ),
                        authStorageDataSource = AuthStorageDataSource
                    )
                ) as T
            }
        }
    }

}
package ru.myitschool.work.ui.qr.result

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.myitschool.work.R
import ru.myitschool.work.data.auth.AuthStorageDataSource
import ru.myitschool.work.data.user.UserNetworkDataSource
import ru.myitschool.work.data.user.UserRepoImpl
import ru.myitschool.work.domain.user.EnterUseCase
import ru.myitschool.work.domain.user.EntranceEntity
import ru.myitschool.work.domain.user.GetUserUseCase
import ru.myitschool.work.domain.user.UserEntity

class QrResultViewModel(
    @ApplicationContext private val context: Context,
    private val enterUseCase: EnterUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    fun updateState(code: String?) {
        viewModelScope.launch {
            _state.emit(State.Loading)
            if (code == null) {
                _state.emit(State.Error(context.getString(R.string.qr_null_result)))
            }
            else {
                _state.emit(
                    enterUseCase.invoke(code).fold(
                        onSuccess = { data ->
                            Log.d("uraa", "успех успех ${data.toString()}")
                            when (data) {
                                true -> State.Show(context.getString(R.string.qr_success_result))
                                false -> State.Show(context.getString(R.string.qr_wrong_result))
                                null -> State.Show(context.getString(R.string.qr_wrong_result))
                            }
                        },
                        onFailure = { error ->
                            Log.d("kaput", error.message.toString())
                            State.Error(error.message.toString())
                        }
                    )
                )
            }
            //_state.emit(State.Error("о нет ошибка ошибка помогите"))
        }
    }

    fun sendResult(code : String) {
        updateState(code)
    }



    sealed interface State {
        data object Loading: State
        data class Show(
            val result : String
        ) : State
        data class Error(
            val text: String
        ) : State
    }
    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                return QrResultViewModel(
                    context = application,
                    enterUseCase = EnterUseCase(
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
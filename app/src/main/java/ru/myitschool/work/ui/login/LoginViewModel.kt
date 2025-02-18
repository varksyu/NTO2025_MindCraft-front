package ru.myitschool.work.ui.login

import LoginUseCase
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.myitschool.work.R
import ru.myitschool.work.domain.auth.IsUserExistUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val isUserExistUseCase: IsUserExistUseCase,
    private  val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(true)
    val state = _state.asStateFlow()

    private val _navigateToMain = MutableSharedFlow<String?>()
    val navigateToMain = _navigateToMain.asSharedFlow()

    private val _userRole = MutableSharedFlow<String>()
    val userRole = _userRole.asSharedFlow()

/*
    init {
        viewModelScope.launch {
            updateState()
        }

    }
    fun auth(
        email : String,
        password : String
    )
    {
        viewModelScope.launch {
            _state.emit(State.Loading)
            when (checkUserExistence(email)) {
                true -> {
                    loginUser(email, password)
                }
                false -> {
                    updateState(context.getString(R.string.error_invalid_credentials))
                }
                null -> updateState(context.getString(R.string.error_unknown))
            }
        }
    }

    private suspend fun checkUserExistence(email: String):Boolean?{
        return try {
            val result = isUserExistUseCase(email)
            result.fold(
                onSuccess = {isExist -> isExist},
                onFailure = {
                    Log.e("AuthViewModel", "Error checking user existence", it)
                    null
                }
            )
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error during user existence check", e)
            null
        }
    }


    private suspend fun loginUser(email: String, password: String) {
        loginUseCase(email, password).fold(
            onSuccess = { user ->
                println("Login successful")
                _userRole.emit(user.authorities)
                _navigateToMain.emit(user.authorities)
            },
            onFailure = { error ->
                updateState(error.message ?: context.getString(R.string.error_unknown))
            }
        )
    }


    private suspend fun updateState(error : String? = null) {
        _state.emit(getStateShow(error))
    }

    private fun getStateShow(error : String? = null) : State {
        return State.Show(
            errorText = error
        )
    }

    fun changeLogin() {
        viewModelScope.launch {
            updateState()
        }
    }

    sealed interface State {
        data object Loading : State
        data class Show(
            var errorText : String?
        ) : State
    }
*/

    /*companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                val authRepoImpl = AuthRepoImpl(
                    authNetworkDataSource = AuthNetworkDataSource,
                    authStorageDataSource = AuthStorageDataSource
                )
                return AuthViewModel(
                    application = application,
                    isUserExistUseCase = IsUserExistUseCase(authRepoImpl),
                    loginUseCase = LoginUseCase(authRepoImpl)
                ) as T
            }

        }
    }*/
}
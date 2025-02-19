package ru.myitschool.work.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.myitschool.work.R
import ru.myitschool.work.data.auth.AuthNetworkDataSource
import ru.myitschool.work.data.auth.AuthRepoImpl
import ru.myitschool.work.data.auth.AuthStorageDataSource
//import ru.myitschool.work.domain.auth.IsUserExistUseCase
import ru.myitschool.work.domain.auth.LoginUseCase
import javax.inject.Inject
import kotlin.reflect.KClass

class LoginViewModel constructor(
    @ApplicationContext private val context: Context,
    private  val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(getStateShow())
    val state = _state.asStateFlow()

    private val _navigateToMain = MutableSharedFlow<String?>()
    val navigateToMain = _navigateToMain.asSharedFlow()

    private val _userRole = MutableSharedFlow<String>()
    val userRole = _userRole.asSharedFlow()


    init {
        viewModelScope.launch {
            updateState()
        }

    }
    fun auth(
        login : String,
        password : String
    )
    {
        viewModelScope.launch {
            loginUser(login, password)

        }
    }



    private suspend fun loginUser(email: String, password: String) {
        loginUseCase(email, password).fold(
            onSuccess = { user ->
                Log.d("loginViewModel","Login successful")
                _userRole.emit(user.authorities)
                _navigateToMain.emit(user.authorities)
            },
            onFailure = { error ->
                updateState(error.message ?: context.getString(R.string.error_unknown))
                Log.d("errorLoginViewModel","${error.message}")
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
        data class Show(
            var errorText : String?
        ) : State
    }


    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                val authRepoImpl = AuthRepoImpl(
                    authNetworkDataSource = AuthNetworkDataSource,
                    authStorageDataSource = AuthStorageDataSource
                )
                return LoginViewModel(
                    application,
                    loginUseCase = LoginUseCase(authRepoImpl)
                ) as T
            }

        }
    }
}
package coding.faizal.ecommerce.core.presentation.viewmodel.authentication

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.di.authentification.ProvideAuthenticationRepositoryImpl
import coding.faizal.ecommerce.core.domain.model.remote.authentication.login.User
import coding.faizal.ecommerce.core.domain.model.remote.authentication.register.Register
import coding.faizal.ecommerce.core.domain.usecase.authentication.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    @ProvideAuthenticationRepositoryImpl private val authenticationUseCase: AuthenticationUseCase
):ViewModel(){

    private val emailInput = MutableStateFlow("")
    private val nameInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")

    val areFieldsValid: Flow<Boolean> = combine(nameInput, passwordInput) { name, password ->
        name.length >= 8 && password.length >= 8
    }

    val isEmailValid: Flow<Boolean> = emailInput
        .map { email ->
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    fun updateEmailInput(email: String) {
        emailInput.value = email
    }

    fun updateFullNameInput(email: String) {
        nameInput.value = email
    }
    fun updatePasswordInput(password: String) {
        passwordInput.value = password
    }

    private val _praRegisterResult = MutableStateFlow<Resource<Register>>(Resource.Loading())
    val praRegisterResult: StateFlow<Resource<Register>> get() = _praRegisterResult

    private val _registerResult = MutableStateFlow<Resource<User>>(Resource.Loading())
    val registerResult: StateFlow<Resource<User>> get() = _registerResult

    private val _loginResult = MutableStateFlow<Resource<User>>(Resource.Loading())
    val loginResult: StateFlow<Resource<User>> get() = _loginResult

    private  val _praLoginResult = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val praLoginResult : StateFlow<Resource<Any>> = _praLoginResult



    fun praRegister(email: String) {
        viewModelScope.launch {
            try {
                _praRegisterResult.value = Resource.Loading()
                val result = authenticationUseCase.praRegister(email).asLiveData().asFlow().first()
                _praRegisterResult.value = result
            } catch (e: Exception) {
                _praRegisterResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun doRegister(email: String,name : String,password: String) {
        viewModelScope.launch {
            try {
                _registerResult.value = Resource.Loading()
                val result = authenticationUseCase.doRegister(email,name,password).asLiveData().asFlow().first()
                _registerResult.value = result
            } catch (e: Exception) {
                _registerResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun praLogin(email: String) {
        viewModelScope.launch {
            try {
                _praLoginResult.value = Resource.Loading()
                val result = authenticationUseCase.praLogin(email).asLiveData().asFlow().first()
                _praLoginResult.value = result
            } catch (e: Exception) {
                _praLoginResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun doLogin(email: String,password: String) {
        viewModelScope.launch {
            try {
                _loginResult.value = Resource.Loading()
                val result = authenticationUseCase.doLogin(email,password).asLiveData().asFlow().first()
                _loginResult.value = result
            } catch (e: Exception) {
                _loginResult.value = Resource.Error(message = e.message)
            }
        }
    }


}
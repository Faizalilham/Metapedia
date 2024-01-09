package coding.faizal.ecommerce.presentation.login.viewmodel


import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.login.ProvideLoginRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ProvideLoginRepositoryImpl private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResult = MutableStateFlow<Resource<User>>(Resource.Loading())
    val loginResult: StateFlow<Resource<User>> get() = _loginResult

    private val emailInput = MutableStateFlow("")

    val isEmailValid: Flow<Boolean> = emailInput
        .map { email ->
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    fun updateEmailInput(email: String) {
        emailInput.value = email
    }

    fun doLogin(email: String,password: String) {
        viewModelScope.launch {
            try {
                _loginResult.value = Resource.Loading()
                val result = loginUseCase.doLogin(email,password).asLiveData().asFlow().first()
                _loginResult.value = result
            } catch (e: Exception) {
                _loginResult.value = Resource.Error(message = e.message)
            }
        }
    }
}

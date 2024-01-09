package coding.faizal.ecommerce.presentation.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.register.ProvideRegisterRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.usecase.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ProvideRegisterRepositoryImpl private val registerUseCase: RegisterUseCase
)  : ViewModel(){

    private val nameInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")

    val areFieldsValid: Flow<Boolean> = combine(nameInput, passwordInput) { name, password ->
        name.length >= 8 && password.length >= 8
    }

    fun updateFullNameInput(email: String) {
        nameInput.value = email
    }
    fun updatePasswordInput(password: String) {
        passwordInput.value = password
    }

    private val _registerResult = MutableStateFlow<Resource<User>>(Resource.Loading())
    val registerResult: StateFlow<Resource<User>> get() = _registerResult

    fun postEmailRegister(token : String, email: String,name : String,password: String) {
        viewModelScope.launch {
            try {
                _registerResult.value = Resource.Loading()
                val result = registerUseCase.doRegister("Bearer $token",email,name,password).asLiveData().asFlow().first()
                _registerResult.value = result
            } catch (e: Exception) {
                _registerResult.value = Resource.Error(message = e.message)
            }
        }
    }

}

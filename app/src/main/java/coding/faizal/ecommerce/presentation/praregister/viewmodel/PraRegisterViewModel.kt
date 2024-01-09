package coding.faizal.ecommerce.presentation.praregister.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.register.ProvideRegisterRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.register.Register
import coding.faizal.ecommerce.domain.usecase.register.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PraRegisterViewModel@Inject constructor(
    @ProvideRegisterRepositoryImpl private val registerUseCase: RegisterUseCase
)  : ViewModel(){

    private val emailInput = MutableStateFlow("")

    val isEmailValid: Flow<Boolean> = emailInput
        .map { email ->
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    fun updateEmailInput(email: String) {
        emailInput.value = email
    }

    private val _registerResult = MutableStateFlow<Resource<Register>>(Resource.Loading())
    val registerResult: StateFlow<Resource<Register>> get() = _registerResult

    fun postEmailRegister(email: String) {
        viewModelScope.launch {
            try {
                _registerResult.value = Resource.Loading()
                val result = registerUseCase.postEmailRegister(email).asLiveData().asFlow().first()
                _registerResult.value = result
            } catch (e: Exception) {
                _registerResult.value = Resource.Error(message = e.message)
            }
        }
    }

}
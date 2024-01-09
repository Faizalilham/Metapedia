package coding.faizal.ecommerce.presentation.pralogin.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.login.ProvideLoginRepositoryImpl
import coding.faizal.ecommerce.di.register.ProvideRegisterRepositoryImpl
import coding.faizal.ecommerce.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PraLoginViewModel @Inject constructor(
    @ProvideLoginRepositoryImpl private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val emailInput = MutableStateFlow("")

    val isEmailValid: Flow<Boolean> = emailInput
        .map { email ->
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    fun updateEmailInput(email: String) {
        emailInput.value = email
    }


    private  val _praLoginResult = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val praLoginResult : StateFlow<Resource<Any>> = _praLoginResult

    fun praLogin(email: String) {
        viewModelScope.launch {
            try {
                _praLoginResult.value = Resource.Loading()
                val result = loginUseCase.praLogin(email).asLiveData().asFlow().first()
                _praLoginResult.value = result
            } catch (e: Exception) {
                _praLoginResult.value = Resource.Error(message = e.message)
            }
        }
    }
}
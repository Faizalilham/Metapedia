package coding.faizal.ecommerce.presentation.forgetpassword.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.forgetpassword.ProvideForgetPasswordRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ForgetPassword
import coding.faizal.ecommerce.domain.usecase.forgetPassword.ForgetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    @ProvideForgetPasswordRepositoryImpl private  val forgetPasswordUseCase: ForgetPasswordUseCase
): ViewModel(){

    private val emailInput = MutableStateFlow("")

    val isEmailValid: Flow<Boolean> = emailInput
        .map { email ->
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    fun updateEmailInput(email: String) {
        emailInput.value = email
    }

    private val _forgetPasswordResult = MutableStateFlow<Resource<ForgetPassword>>(Resource.Loading())
    val forgetPasswordResult : StateFlow<Resource<ForgetPassword>> = _forgetPasswordResult

    fun forgetPassword(email : String){
        viewModelScope.launch {
            try {
                _forgetPasswordResult.value = Resource.Loading()
                val result = forgetPasswordUseCase.forgetPassword(email).asLiveData().asFlow().first()
                _forgetPasswordResult.value = result
            } catch (e: Exception) {
                _forgetPasswordResult.value = Resource.Error(message = e.message)
            }
        }
    }
}
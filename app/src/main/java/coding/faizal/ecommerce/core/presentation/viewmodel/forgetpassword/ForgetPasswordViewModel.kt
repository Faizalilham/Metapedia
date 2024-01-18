package coding.faizal.ecommerce.core.presentation.viewmodel.forgetpassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.password.ForgetPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.NewPassword
import coding.faizal.ecommerce.core.domain.model.remote.password.ResetPassword
import coding.faizal.ecommerce.di.forgetpassword.ProvideForgetPasswordRepositoryImpl
import coding.faizal.ecommerce.core.domain.usecase.forgetPassword.ForgetPasswordUseCase
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

    private val _resetPasswordResult = MutableStateFlow<Resource<ResetPassword>>(Resource.Loading())
    val resetPasswordResult : StateFlow<Resource<ResetPassword>> = _resetPasswordResult

    private val _newPasswordResult = MutableStateFlow<Resource<NewPassword>>(Resource.Loading())
    val newPasswordResult : StateFlow<Resource<NewPassword>> = _newPasswordResult

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

    fun resetPassword(newPassword : String){
        viewModelScope.launch {
            try {
                _resetPasswordResult.value = Resource.Loading()
                val result = forgetPasswordUseCase.resetPassword(newPassword).asLiveData().asFlow().first()
                _resetPasswordResult.value = result
            } catch (e: Exception) {
                _resetPasswordResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun newPassword(otp: String,password : String){
        viewModelScope.launch {
            try {
                _newPasswordResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = forgetPasswordUseCase.createNewPassword(otp,password).asLiveData().asFlow().first()
                _newPasswordResult.value = result
            } catch (e: Exception) {
                _newPasswordResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

}
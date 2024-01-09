package coding.faizal.ecommerce.presentation.newpassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.forgetpassword.ProvideForgetPasswordRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ForgetPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.NewPassword
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.usecase.forgetPassword.ForgetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewPasswordViewModel @Inject constructor(
    @ProvideForgetPasswordRepositoryImpl private val forgetPasswordUseCase: ForgetPasswordUseCase
) : ViewModel(){

    private val _newPasswordResult = MutableStateFlow<Resource<NewPassword>>(Resource.Loading())
    val newPasswordResult : StateFlow<Resource<NewPassword>> = _newPasswordResult

    fun newPassword(otp: String,password : String){
        viewModelScope.launch {
            try {
                _newPasswordResult.value = Resource.Loading()
                val result = forgetPasswordUseCase.createNewPassword(otp,password).asLiveData().asFlow().first()
                _newPasswordResult.value = result
            } catch (e: Exception) {
                _newPasswordResult.value = Resource.Error(message = e.message)
            }
        }
    }
}
package coding.faizal.ecommerce.presentation.baseedit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.response.forgetPassword.ResetPasswordRequest
import coding.faizal.ecommerce.di.forgetpassword.ProvideForgetPasswordRepositoryImpl
import coding.faizal.ecommerce.di.profile.ProvideProfileRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ResetPassword
import coding.faizal.ecommerce.domain.usecase.forgetPassword.ForgetPasswordUseCase
import coding.faizal.ecommerce.domain.usecase.profile.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseEditViewModel @Inject constructor(
    @ProvideProfileRepositoryImpl private val profileUseCase: ProfileUseCase,
    @ProvideForgetPasswordRepositoryImpl private val forgetPasswordUseCase: ForgetPasswordUseCase
): ViewModel() {

    private val _updateUsernameResult = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val updateUsernameResult : StateFlow<Resource<Any>> = _updateUsernameResult

    private val _resetPasswordResult = MutableStateFlow<Resource<ResetPassword>>(Resource.Loading())
    val resetPasswordResult : StateFlow<Resource<ResetPassword>> = _resetPasswordResult

    fun updateUsername(token : String,username : String){
        viewModelScope.launch {
            try {
                _updateUsernameResult.value = Resource.Loading()
                val result = profileUseCase.updateUsername(token,username).asLiveData().asFlow().first()
                _updateUsernameResult.value = result
            } catch (e: Exception) {
                _updateUsernameResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun resetPassword(token : String,newPassword : String){
        viewModelScope.launch {
            try {
                _resetPasswordResult.value = Resource.Loading()
                val result = forgetPasswordUseCase.resetPassword(token,newPassword).asLiveData().asFlow().first()
                _resetPasswordResult.value = result
            } catch (e: Exception) {
                _resetPasswordResult.value = Resource.Error(message = e.message)
            }
        }
    }
}
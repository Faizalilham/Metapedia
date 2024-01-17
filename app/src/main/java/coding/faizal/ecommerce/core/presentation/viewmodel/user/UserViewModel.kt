package coding.faizal.ecommerce.core.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.di.user.ProvideUserRepositoryImpl
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    @ProvideUserRepositoryImpl private val userUseCase: UserUseCase
) : ViewModel(){

    private val _profileResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val profileResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser>> = _profileResult

    private val _updateUsernameResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<Any>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val updateUsernameResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<Any>> = _updateUsernameResult

    private val _addAddressResult = MutableStateFlow<coding.faizal.ecommerce.core.data.Resource<Any>>(
        coding.faizal.ecommerce.core.data.Resource.Loading())
    val addAddressResult : StateFlow<coding.faizal.ecommerce.core.data.Resource<Any>> = _addAddressResult

    private val _labelAddress = MutableStateFlow<List<coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress>>(emptyList())
    val productSizeData : StateFlow<List<coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress>> get() = _labelAddress

    fun getCurrentUser(token : String){
        viewModelScope.launch {
            try {
                _profileResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = userUseCase.getCurrentUser(token).asLiveData().asFlow().first()
                _profileResult.value = result
            } catch (e: Exception) {
                _profileResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun updateUsername(token : String,username : String){
        viewModelScope.launch {
            try {
                _updateUsernameResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = userUseCase.updateUsername(token,username).asLiveData().asFlow().first()
                _updateUsernameResult.value = result
            } catch (e: Exception) {
                _updateUsernameResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }

    fun getAllLabelAddressData() {
        viewModelScope.launch {
            _labelAddress.value = userUseCase.getAllLabelAddress()
        }
    }

    fun updateAddress(token : String,address : List<coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress>){
        viewModelScope.launch {
            try {
                _addAddressResult.value = coding.faizal.ecommerce.core.data.Resource.Loading()
                val result = userUseCase.updateAddress(token,address).asLiveData().asFlow().first()
                _addAddressResult.value = result
            } catch (e: Exception) {
                _addAddressResult.value = coding.faizal.ecommerce.core.data.Resource.Error(message = e.message)
            }
        }
    }
    
    

}
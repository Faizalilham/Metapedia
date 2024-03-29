package coding.faizal.ecommerce.core.presentation.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.di.user.ProvideUserRepositoryImpl
import coding.faizal.ecommerce.core.domain.usecase.user.UserUseCase
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

    private val _profileResult = MutableStateFlow<Resource<ProfileUser>>(Resource.Loading())
    val profileResult : StateFlow<Resource<ProfileUser>> = _profileResult

    private val _updateUsernameResult = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val updateUsernameResult : StateFlow<Resource<Any>> = _updateUsernameResult

    private val _addAddressResult = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val addAddressResult : StateFlow<Resource<Any>> = _addAddressResult

    private val _labelAddress = MutableStateFlow<List<LabelAddress>>(emptyList())
    val productSizeData : StateFlow<List<LabelAddress>> get() = _labelAddress

    fun getCurrentUser(){
        viewModelScope.launch {
            try {
                _profileResult.value = Resource.Loading()
                val result = userUseCase.getCurrentUser().asLiveData().asFlow().first()
                _profileResult.value = result
            } catch (e: Exception) {
                _profileResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun updateUsername(username : String){
        viewModelScope.launch {
            try {
                _updateUsernameResult.value = Resource.Loading()
                val result = userUseCase.updateUsername(username).asLiveData().asFlow().first()
                _updateUsernameResult.value = result
            } catch (e: Exception) {
                _updateUsernameResult.value = Resource.Error(message = e.message)
            }
        }
    }

    fun getAllLabelAddressData() {
        viewModelScope.launch {
            _labelAddress.value = userUseCase.getAllLabelAddress()
        }
    }

    fun updateAddress(address : List<UserAddress>){
        viewModelScope.launch {
            try {
                _addAddressResult.value = Resource.Loading()
                val result = userUseCase.updateAddress(address).asLiveData().asFlow().first()
                _addAddressResult.value = result
            } catch (e: Exception) {
                _addAddressResult.value = Resource.Error(message = e.message)
            }
        }
    }
    
    

}
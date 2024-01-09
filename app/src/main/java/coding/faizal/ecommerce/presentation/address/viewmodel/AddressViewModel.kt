package coding.faizal.ecommerce.presentation.address.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.address.ProvideAddressRepositoryImpl
import coding.faizal.ecommerce.di.profile.ProvideProfileRepositoryImpl
import coding.faizal.ecommerce.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.domain.usecase.address.AddressUseCase
import coding.faizal.ecommerce.domain.usecase.profile.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(
    @ProvideProfileRepositoryImpl private val profileUseCase: ProfileUseCase,
    @ProvideAddressRepositoryImpl private val addressUseCase: AddressUseCase
        ) : ViewModel(){

    private val _addAddressResult = MutableStateFlow<Resource<Any>>(Resource.Loading())
    val addAddressResult : StateFlow<Resource<Any>> = _addAddressResult

    private val _labelAddress = MutableStateFlow<List<LabelAddress>>(emptyList())
    val productSizeData : StateFlow<List<LabelAddress>> get() = _labelAddress

    fun getAllLabelAddressData() {
        viewModelScope.launch {
            _labelAddress.value = addressUseCase.getAllLabelAddress()
        }
    }

    fun updateAddress(token : String,address : List<UserAddress>){
        viewModelScope.launch {
            try {
                _addAddressResult.value = Resource.Loading()
                val result = profileUseCase.updateAddress(token,address).asLiveData().asFlow().first()
                _addAddressResult.value = result
            } catch (e: Exception) {
                _addAddressResult.value = Resource.Error(message = e.message)
            }
        }
    }
}
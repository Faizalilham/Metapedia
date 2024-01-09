package coding.faizal.ecommerce.presentation.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.profile.ProvideProfileRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.domain.usecase.profile.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ProvideProfileRepositoryImpl private val profileUseCase: ProfileUseCase
) : ViewModel(){

    private val _profileResult = MutableStateFlow<Resource<ProfileUser>>(Resource.Loading())
    val profileResult : StateFlow<Resource<ProfileUser>> = _profileResult

    fun getCurrentUser(token : String){
        viewModelScope.launch {
            try {
                _profileResult.value = Resource.Loading()
                val result = profileUseCase.getCurrentUser(token).asLiveData().asFlow().first()
                _profileResult.value = result
            } catch (e: Exception) {
                _profileResult.value = Resource.Error(message = e.message)
            }
        }
    }

}
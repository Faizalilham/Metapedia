package coding.faizal.ecommerce.presentation.menu.viewmodel

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
class MenuViewModel @Inject constructor(
    @ProvideProfileRepositoryImpl private val profileUseCase: ProfileUseCase) : ViewModel(){

    private val _menuResult = MutableStateFlow<Resource<ProfileUser>>(Resource.Loading())
    val menuResult : StateFlow<Resource<ProfileUser>> = _menuResult

    fun getCurrentUser(token : String){
        viewModelScope.launch {
            try {
                _menuResult.value = Resource.Loading()
                val result = profileUseCase.getCurrentUser(token).asLiveData().asFlow().first()
                _menuResult.value = result
            } catch (e: Exception) {
                _menuResult.value = Resource.Error(message = e.message)
            }
        }
    }

}
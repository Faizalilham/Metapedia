package coding.faizal.ecommerce.presentation.help.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.di.help.ProvideHelpRepositoryImpl
import coding.faizal.ecommerce.domain.model.local.help.Help
import coding.faizal.ecommerce.domain.usecase.help.HelpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HelpViewModel @Inject constructor(
    @ProvideHelpRepositoryImpl private val helpUseCase: HelpUseCase
) : ViewModel() {

    private val _helpData = MutableStateFlow<List<Help>>(emptyList())
    val helpData: StateFlow<List<Help>> get() = _helpData

    fun getAllHelpData() {
        viewModelScope.launch {
            _helpData.value = helpUseCase.getAllData()
        }
    }

}
package coding.faizal.ecommerce.presentation.verification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResendCodeViewModel : ViewModel() {
    private val _resendTimeLeft = MutableStateFlow(30)
    val resendTimeLeft: StateFlow<Int> = _resendTimeLeft.asStateFlow()

    init {
        startCountdown()
    }

    fun startCountdown() {
        viewModelScope.launch {
            while (_resendTimeLeft.value > 0) {
                delay(1000)
                _resendTimeLeft.value--
            }
        }
    }
}
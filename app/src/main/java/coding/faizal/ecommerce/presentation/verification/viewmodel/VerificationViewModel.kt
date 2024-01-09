package coding.faizal.ecommerce.presentation.verification.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.di.verification.ProvideVerificationRepositoryImpl
import coding.faizal.ecommerce.domain.model.remote.verify.Verification
import coding.faizal.ecommerce.domain.usecase.verification.VerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    @ProvideVerificationRepositoryImpl private val verificationUseCase: VerificationUseCase
) : ViewModel() {
    private val _resendTimeLeft = MutableStateFlow(30)
    val resendTimeLeft: StateFlow<Int> = _resendTimeLeft.asStateFlow()

    private val otpInput = MutableStateFlow("")

    val isOtpValid: Flow<Boolean> = otpInput
        .map { otp ->
            otp.length == 6
        }

    fun updateOtpInput(otp: String) {
        otpInput.value = otp
    }

    private val _verificationResult = MutableStateFlow<Resource<Verification>>(Resource.Loading())
    val verificationResult: StateFlow<Resource<Verification>> get() = _verificationResult

    fun postVerification(otp: String) {
        viewModelScope.launch {
            try {
                _verificationResult.value = Resource.Loading()
                val result = verificationUseCase.sendOtpVerification(otp).asLiveData().asFlow().first()
                _verificationResult.value = result
            } catch (e: Exception) {
                _verificationResult.value = Resource.Error(message = e.message)
            }
        }
    }

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
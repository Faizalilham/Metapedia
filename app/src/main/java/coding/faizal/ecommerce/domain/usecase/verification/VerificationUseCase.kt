package coding.faizal.ecommerce.domain.usecase.verification

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.verify.Verification
import kotlinx.coroutines.flow.Flow

interface VerificationUseCase {

    fun sendOtpVerification(otp : String) : Flow<Resource<Verification>>

}
package coding.faizal.ecommerce.core.domain.usecase.verification

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.verify.Verification
import kotlinx.coroutines.flow.Flow

interface VerificationUseCase {

    fun sendOtpVerification(otp : String) : Flow<Resource<Verification>>

}
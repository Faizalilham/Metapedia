package coding.faizal.ecommerce.core.domain.repository.verification

import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.domain.model.remote.verify.Verification
import kotlinx.coroutines.flow.Flow

interface IVerificationRepository {

    fun sendOtpVerification(otp : String) : Flow<Resource<Verification>>
}
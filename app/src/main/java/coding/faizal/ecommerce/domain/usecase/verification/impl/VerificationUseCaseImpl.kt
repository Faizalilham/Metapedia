package coding.faizal.ecommerce.domain.usecase.verification.impl

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.verify.Verification
import coding.faizal.ecommerce.domain.repository.verification.IVerificationRepository
import coding.faizal.ecommerce.domain.usecase.verification.VerificationUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerificationUseCaseImpl @Inject constructor(
    private val IVerificationRepository : IVerificationRepository
): VerificationUseCase {
    override fun sendOtpVerification(otp: String): Flow<Resource<Verification>>  = IVerificationRepository.sendOtpVerification(otp)
}
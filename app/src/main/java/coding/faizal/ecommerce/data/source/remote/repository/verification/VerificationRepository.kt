package coding.faizal.ecommerce.data.source.remote.repository.verification

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.data.source.remote.response.verification.VerificationRequest
import coding.faizal.ecommerce.domain.model.remote.verify.Verification
import coding.faizal.ecommerce.domain.repository.verification.IVerificationRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper.mapFromVerificationResponseToEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerificationRepository @Inject constructor(
    private val apiAuthService: ApiAuthService
) : IVerificationRepository {
    override fun sendOtpVerification(otp: String): Flow<Resource<Verification>> = flow {
        val errorHandling = ErrorHandling<Verification>(this)
        try {
            val response = apiAuthService.verificationOtp(VerificationRequest(otp))

            if (response.success) {
                val verificationResult = response.data
                emit(Resource.Success(mapFromVerificationResponseToEntities(verificationResult), response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)



}


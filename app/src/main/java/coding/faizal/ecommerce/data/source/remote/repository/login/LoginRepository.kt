package coding.faizal.ecommerce.data.source.remote.repository.login

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.data.source.remote.response.login.LoginRequest
import coding.faizal.ecommerce.data.source.remote.response.login.PraLoginRequest
import coding.faizal.ecommerce.data.source.remote.response.verification.VerificationRequest
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.model.remote.verify.Verification
import coding.faizal.ecommerce.domain.repository.login.ILoginRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper
import coding.faizal.ecommerce.utils.DataMapper.mapFromLoginResponseToEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository  @Inject constructor(private val apiAuthService: ApiAuthService) : ILoginRepository{
    override fun praLogin(email: String): Flow<Resource<Any>> = flow{
        val errorHandling = ErrorHandling<Any>(this)
        try {
            val response = apiAuthService.praLogin(PraLoginRequest(email))

            if (response.success) {
                val praLoginResult = response.data
                emit(Resource.Success(praLoginResult,response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun doLogin(email: String, password: String) : Flow<Resource<User>> = flow{
        val errorHandling = ErrorHandling<User>(this)
        try {
            val response = apiAuthService.doLogin(LoginRequest(email,password))

            if (response.success) {
                val loginResult = response.data.user
                emit(Resource.Success(mapFromLoginResponseToEntities(loginResult),response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)
}
package coding.faizal.ecommerce.core.data.source.remote.repository.authentication

import android.util.Log
import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.login.LoginRequest
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.pralogin.PraLoginRequest
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.praregister.RegisterRequest
import coding.faizal.ecommerce.core.data.source.remote.response.authentication.register.RegisterRequestAfterVerification
import coding.faizal.ecommerce.core.domain.model.remote.authentication.login.User
import coding.faizal.ecommerce.core.domain.model.remote.authentication.register.Register
import coding.faizal.ecommerce.core.domain.repository.authentication.IAuthenticationRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepository @Inject constructor(private val apiAuthService: ApiAuthService) :
    IAuthenticationRepository {
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
                emit(Resource.Success(DataMapper.mapFromLoginResponseToEntities(loginResult),response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun praRegister(email: String): Flow<Resource<Register>> = flow {
        try {
            val response = apiAuthService.praRegister(RegisterRequest(email))
            Log.d("ERROR RESPONSES", "$response")

            if (response.success) {
                val registerResult = response.data
                emit(Resource.Success(DataMapper.mapFromRegisterResponseToEntities(registerResult), response.message))
            } else {
                val errorMessage = response.message
                if (response.code == "400") {
                    Log.d("ERROR BODY", "Bad Request: $errorMessage")

                    emit(Resource.Error(message = errorMessage))
                } else {
                    emit(Resource.Error(message = errorMessage))
                }
            }
        } catch (e: HttpException) {
            val errorBodyString = e.response()?.errorBody()?.string()
            Log.d("ERROR BODY", "Bad Request: $errorBodyString")

            try {
                val jsonElement = JsonParser.parseString(errorBodyString)
                val message = jsonElement.asJsonObject.get("message").asString

                emit(Resource.Error(message = message))
            } catch (jsonException: Exception) {
                emit(Resource.Error(message = jsonException.message))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun doRegister(token : String,email: String, name: String, password: String): Flow<Resource<User>>  = flow {
        val errorHandling = ErrorHandling<User>(this)
        try {
            val response = apiAuthService.doRegister(token,
                RegisterRequestAfterVerification(email,name,password)
            )

            if (response.success) {
                val registerResult = response.data
                emit(Resource.Success(DataMapper.mapFromRegisterResponseToEntities(registerResult),response.message))

            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)
}
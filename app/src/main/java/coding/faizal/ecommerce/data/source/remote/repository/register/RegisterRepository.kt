package coding.faizal.ecommerce.data.source.remote.repository.register

import android.util.Log
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.data.source.remote.response.register.RegisterRequest
import coding.faizal.ecommerce.data.source.remote.response.register.RegisterRequestAfterVerification
import coding.faizal.ecommerce.domain.model.remote.register.Register
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.repository.register.IRegisterRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper
import coding.faizal.ecommerce.utils.DataMapper.mapFromRegisterResponseToEntities
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository  @Inject constructor(
 private val apiAuthService: ApiAuthService
) : IRegisterRepository{
    override fun postEmailRegister(email: String): Flow<Resource<Register>> = flow {
        try {
            val response = apiAuthService.praRegister(RegisterRequest(email))
            Log.d("ERROR RESPONSES", "$response")

            if (response.success) {
                val registerResult = response.data
                emit(Resource.Success(mapFromRegisterResponseToEntities(registerResult), response.message))
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
            val response = apiAuthService.doRegister(token,RegisterRequestAfterVerification(email,name,password))

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
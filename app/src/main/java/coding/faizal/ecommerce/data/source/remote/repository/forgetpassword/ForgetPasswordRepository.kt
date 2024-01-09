package coding.faizal.ecommerce.data.source.remote.repository.forgetpassword

import android.util.Log
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.data.source.remote.response.forgetPassword.ForgetPasswordRequest
import coding.faizal.ecommerce.data.source.remote.response.forgetPassword.NewPasswordRequest
import coding.faizal.ecommerce.data.source.remote.response.forgetPassword.ResetPasswordRequest
import coding.faizal.ecommerce.data.source.remote.response.register.RegisterRequestAfterVerification
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ForgetPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.NewPassword
import coding.faizal.ecommerce.domain.model.remote.forgetpassword.ResetPassword
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.repository.forgetpassword.IForgetPasswordRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper
import coding.faizal.ecommerce.utils.DataMapper.mapFromCreateNewPasswordResponseToEntities
import coding.faizal.ecommerce.utils.DataMapper.mapFromForgetPasswordResponseToEntities
import coding.faizal.ecommerce.utils.DataMapper.mapFromResetPasswordResponseToEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForgetPasswordRepository  @Inject constructor(
    private val apiAuthService: ApiAuthService
) : IForgetPasswordRepository {
    override fun forgetPassword(email: String): Flow<Resource<ForgetPassword>> = flow {
        val errorHandling = ErrorHandling<ForgetPassword>(this)
        try {
            val response = apiAuthService.forgetPassword(ForgetPasswordRequest(email))

            if (response.success) {
                val registerResult = response.data
                emit(Resource.Success(mapFromForgetPasswordResponseToEntities(registerResult),response.message))

            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun createNewPassword(otp: String, password: String): Flow<Resource<NewPassword>>  = flow{
        val errorHandling = ErrorHandling<NewPassword>(this)
        try {
            val response = apiAuthService.createNewPassword(NewPasswordRequest(otp.toInt(),password))

            if (response.success) {
                val newPasswordResult = response.data
                Log.d("RESULT","$newPasswordResult")
                emit(Resource.Success(mapFromCreateNewPasswordResponseToEntities(newPasswordResult.user),response.message))

            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun resetPassword(token: String, newPassword: String): Flow<Resource<ResetPassword>> = flow {
        val errorHandling = ErrorHandling<ResetPassword>(this)
        try {
            val response = apiAuthService.resetPassword(token,ResetPasswordRequest(newPassword))

            if (response.success) {
                val newPasswordResult = response.data
                Log.d("RESULT","$newPasswordResult")
                emit(Resource.Success(mapFromResetPasswordResponseToEntities(newPasswordResult.user),response.message))

            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)
}
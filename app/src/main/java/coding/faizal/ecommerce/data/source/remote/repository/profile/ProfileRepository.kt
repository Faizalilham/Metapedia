package coding.faizal.ecommerce.data.source.remote.repository.profile

import android.util.Log
import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.data.source.remote.response.profile.AddressRequest
import coding.faizal.ecommerce.data.source.remote.response.profile.EditProfileRequest
import coding.faizal.ecommerce.data.source.remote.response.register.RegisterRequestAfterVerification
import coding.faizal.ecommerce.domain.model.remote.login.User
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.domain.repository.profile.IProfileRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper
import coding.faizal.ecommerce.utils.DataMapper.mapFromProfileUserResponseToEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProfileRepository @Inject constructor(private val apiAuthService: ApiAuthService) : IProfileRepository{
    override fun getCurrentUser(token: String): Flow<Resource<ProfileUser>>  = flow {
        val errorHandling = ErrorHandling<ProfileUser>(this)
        try {
            val response = apiAuthService.getCurrentUser(token)
            if (response.success) {
                val profileResult = response.data
                emit(Resource.Success(mapFromProfileUserResponseToEntities(profileResult),response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun updateUsername(token: String,username : String): Flow<Resource<Any>> = flow {
        val errorHandling = ErrorHandling<Any>(this)
        try {
            val response = apiAuthService.updateProfileUsername(token, EditProfileRequest(username))
            if (response.success) {
                val profileResult = response.data
                emit(Resource.Success(profileResult,response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun updateAddress(token: String,address : List<UserAddress>): Flow<Resource<Any>> = flow {
        val errorHandling = ErrorHandling<Any>(this)
        try {
            val response = apiAuthService.updateProfileAddress(token, AddressRequest(address))
            if (response.success) {
                val profileResult = response.data
                emit(Resource.Success(profileResult,response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

    override fun getListAddress(token: String): Flow<Resource<List<UserAddress>>> = flow{
        val errorHandling = ErrorHandling<List<UserAddress>>(this)
        try {
            val response = apiAuthService.getCurrentUser(token)
            if (response.success) {
                val profileResult = response.data.addresses
                emit(Resource.Success(profileResult,response.message))
            } else {
                errorHandling.handleError(response.code, response.message)
            }
        } catch (e: HttpException) {
            errorHandling.handleHttpException(e)
        }
    }.flowOn(Dispatchers.IO)

}
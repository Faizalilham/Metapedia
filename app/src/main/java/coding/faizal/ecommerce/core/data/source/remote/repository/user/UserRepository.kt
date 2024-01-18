package coding.faizal.ecommerce.core.data.source.remote.repository.user



import coding.faizal.ecommerce.core.data.Resource
import coding.faizal.ecommerce.core.data.source.remote.network.auth.ApiAuthService
import coding.faizal.ecommerce.core.data.source.remote.response.user.address.AddressRequest
import coding.faizal.ecommerce.core.data.source.remote.response.user.profile.EditProfileRequest
import coding.faizal.ecommerce.core.domain.model.local.address.LabelAddress
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.core.domain.model.remote.profileuser.UserAddress
import coding.faizal.ecommerce.core.domain.repository.user.IUserRepository
import coding.faizal.ecommerce.failure.ErrorHandling
import coding.faizal.ecommerce.utils.DataMapper.mapFromProfileUserResponseToEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(private val apiAuthService: ApiAuthService) :
    IUserRepository {
    override fun getCurrentUser(): Flow<Resource<ProfileUser>>  = flow {
        val errorHandling = ErrorHandling<ProfileUser>(this)
        try {
            val response = apiAuthService.getCurrentUser()
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

    override fun updateUsername(username : String): Flow<Resource<Any>> = flow {
        val errorHandling = ErrorHandling<Any>(this)
        try {
            val response = apiAuthService.updateProfileUsername(EditProfileRequest(username))
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

    override fun updateAddress(address : List<UserAddress>): Flow<Resource<Any>> = flow {
        val errorHandling = ErrorHandling<Any>(this)
        try {
            val response = apiAuthService.updateProfileAddress(AddressRequest(address))
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

    override fun getListAddress(): Flow<Resource<List<UserAddress>>> = flow{
        val errorHandling = ErrorHandling<List<UserAddress>>(this)
        try {
            val response = apiAuthService.getCurrentUser()
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

    override fun getAllLabelAddress(): List<LabelAddress> {
        return listOf(
            LabelAddress(
                1,
                "Rumah",
                false,
            ),
            LabelAddress(
                2,
                "Apartemen",
                false,
            ),
            LabelAddress(
                3,
                "Kantor",
                false,
            ),
            LabelAddress(
                4,
                "Kos",
                false,
            ),
        )
    }

}
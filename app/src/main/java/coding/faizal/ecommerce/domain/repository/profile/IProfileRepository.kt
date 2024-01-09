package coding.faizal.ecommerce.domain.repository.profile

import coding.faizal.ecommerce.data.Resource
import coding.faizal.ecommerce.domain.model.remote.profileuser.ProfileUser
import coding.faizal.ecommerce.domain.model.remote.profileuser.UserAddress
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {

    fun getCurrentUser(token : String) : Flow<Resource<ProfileUser>>

    fun updateUsername(token : String,username : String) : Flow<Resource<Any>>

    fun updateAddress(token : String,address : List<UserAddress>) :  Flow<Resource<Any>>

    fun getListAddress(token : String) : Flow<Resource<List<UserAddress>>>
}